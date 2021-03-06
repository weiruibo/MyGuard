package cn.edu.gdmec.w07150837.myguard.m8trafficmonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.edu.gdmec.w07150837.myguard.R;
import cn.edu.gdmec.w07150837.myguard.m8trafficmonitor.db.dao.TrafficDao;
import cn.edu.gdmec.w07150837.myguard.m8trafficmonitor.service.TrafficMonitoringService;
import cn.edu.gdmec.w07150837.myguard.m8trafficmonitor.utils.SystemInfoUtils;

public class TrafficMonitoringActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG = "m8trafficmonitoringActivity";

    private SharedPreferences mSP;
    private Button mCorrectFlowBtn;
    private TextView mTotalTV;
    private TextView mUsedTV;
    private TextView mToDayTV;
    private TrafficDao dao;
    private ImageView mRemindIMGV;
    private TextView mRemindTV;
    private CorrectFlowReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_traffic_monitoring);
        mSP = getSharedPreferences("config", MODE_PRIVATE);
        boolean flag = mSP.getBoolean("isset_operator", false);
        //如果没有设置营运商信息则进入信息设置页面
        if (!flag) {
            startActivity(new Intent(this, OperatorSetActivity.class));
            finish();
        }
        if (!SystemInfoUtils.isServiceRunning(this, "cn.edu.gdmec.w07150837.myguard" +
                ".m8trafficmonitor.service.TrafficMonitoringService")) {
            startService(new Intent(this, TrafficMonitoringService.class));
        }
        initView();
        registReceiver();
        initData();

    }

    private void initView() {

        findViewById(R.id.r1_titlebar).setBackgroundColor(getResources().getColor(R.color
                .light_green));
        ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
        ((TextView) findViewById(R.id.tv_title)).setText("流量监控");
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back);
        mCorrectFlowBtn = (Button) findViewById(R.id.btn_correction_flow);
        mCorrectFlowBtn.setOnClickListener(this);
        mTotalTV = (TextView) findViewById(R.id.tv_month_totalgprs);
        mUsedTV = (TextView) findViewById(R.id.tv_month_usedgprs);
        mToDayTV = (TextView) findViewById(R.id.tv_today_gprs);
        mRemindIMGV = (ImageView) findViewById(R.id.imgv_traffic_remind);
        mRemindTV = (TextView) findViewById(R.id.tv_traffic_remind);

    }

    private void initData() {

        long totalflow = mSP.getLong("totalflow", 0);
        long usedflow = mSP.getLong("usedflow", 0);
        if (totalflow > 0 & usedflow >= 0) {
            float scale = usedflow / totalflow;
            if (scale > 0.9) {
                mRemindIMGV.setEnabled(false);
                mRemindTV.setText("你的流量即将用完！");
            } else {
                mRemindIMGV.setEnabled(true);
                mRemindTV.setText("本月流量充足，请放心使用");
            }

        }
        mTotalTV.setText("本月流量：" + Formatter.formatFileSize(this, totalflow));
        mUsedTV.setText("本月已用：" + Formatter.formatFileSize(this, usedflow));
        dao = new TrafficDao(this);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dataString = sdf.format(date);
        long moblieGPRS = dao.getMoblieGPRS(dataString);
        if (moblieGPRS < 0) {
            moblieGPRS = 0;
        }
        mToDayTV.setText("本日已用：" + Formatter.formatFileSize(this, moblieGPRS));

    }

    private void registReceiver() {

        receiver = new CorrectFlowReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(receiver, filter);
        Log.d(TAG, "注册短信广播成功");

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.imgv_leftbtn:
                finish();
                break;
            case R.id.btn_correction_flow:
                //首先判断是哪个运营商
                int i = mSP.getInt("operator", 0);
                SmsManager smsManager = SmsManager.getDefault();
                switch (i) {
                    case 0:
                        Toast.makeText(this, "您还没有设置运营商信息", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        //中国移动
                        smsManager.sendTextMessage("10086", null, "CXLL", null, null);
                        break;
                    case 2:
                        //中国联通
                        //发送CXLL至10010
                        //获取系统默认的短信管理器
                        smsManager.sendTextMessage("10010", null, "CXLL", null, null);
                        break;
                    case 3:
                        //中国电信
                        break;

                }

                break;
        }

    }

    class CorrectFlowReceiver extends BroadcastReceiver {
        String body;
        String address;

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "接收到短信广播");
            Object[] objs = (Object[]) intent.getExtras().get("pdus");
            for (Object obj : objs) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
                body += smsMessage.getMessageBody();
                address = smsMessage.getOriginatingAddress();
                //一下短信分割只针对联通3G用户
                //  Log.d(TAG,body);

            }

            if (address.equals("10010")) {
                // Log.d("m8trafficmonitoringActivity", body + "");
                String[] split = body.split("；");
                //本月剩余流量
                long out = 0;
                long in = 0;
                //本月已用流量
                long totalUsed = 0;
                //本月超出流量
                long beyond = 0;
                for (int i = 0; i < split.length; i++) {
                    Log.d("m8" + i, split[i]);
                    if (split[i].contains("本月总流量已用")) {
                        //套餐总量
                        String usedflow = split[i].substring(split[i].lastIndexOf("：") + 8, split[i].length());
                        totalUsed = getStringTofloat(usedflow);
                        Log.d(TAG + "本月总流量已用", usedflow);
                    } else if (split[i].contains("省外流量")) {
                        String outflow = split[i].substring(split[i].lastIndexOf("，") + 3, split[i].length());
                        out = getStringTofloat(outflow);
                        Log.d(TAG + "省外流量", outflow + "");
                    } else if (split[i].contains("定向流量")) {
                        String inflow = split[i].substring(split[i].lastIndexOf("，") + 3, split[i].length());
                        in = getStringTofloat(inflow);
                        Log.d(TAG + "定向流量", inflow + "");

                    } else if (split[i].contains("套餐外收费流量已用")) {
                        String beyondflow = split[i].substring(9, split[i].length());
                        beyond = getStringTofloat(beyondflow);
                        Log.d(TAG + "套餐外收费流量已用", beyondflow);

                    }

                }
                SharedPreferences.Editor edit = mSP.edit();
                edit.putLong("totalflow", totalUsed + out + in);
                edit.putLong("usedflow", totalUsed);
                edit.commit();
                mTotalTV.setText("本月流量：" + Formatter.formatFileSize(context, (totalUsed + out + in)));
                mUsedTV.setText("本月已用：" + Formatter.formatFileSize(context, (totalUsed)));

            }

        }

    }

    //将字符串转化成Float类型数据
    private long getStringTofloat(String str) {
        long flow = 0;
        if (!TextUtils.isEmpty(str)) {
            if (str.contains("KB")) {
                String[] split = str.split("KB");
                float m = Float.parseFloat(split[0]);
                flow = (long) (m * 1024);
            } else if (str.contains("MB")) {
                String[] split = str.split("MB");
                float m = Float.parseFloat(split[0]);
                flow = (long) (m * 1024 * 1024);
            } else if (str.contains("GB")) {
                String[] spit = str.split("GB");
                float m = Float.parseFloat(spit[0]);
                flow = (long) (m * 1024 * 1024 * 1024);
            }
        }

        return flow;
    }

    @Override
    protected void onDestroy() {

        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy();
    }
}
