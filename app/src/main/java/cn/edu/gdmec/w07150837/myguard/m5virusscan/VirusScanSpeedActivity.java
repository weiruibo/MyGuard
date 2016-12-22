package cn.edu.gdmec.w07150837.myguard.m5virusscan;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.edu.gdmec.w07150837.myguard.R;
import cn.edu.gdmec.w07150837.myguard.m5virusscan.adapter.ScanVirusAdapter;
import cn.edu.gdmec.w07150837.myguard.m5virusscan.dao.AntiVirusDao;
import cn.edu.gdmec.w07150837.myguard.m5virusscan.entity.ScanAppInfo;
import cn.edu.gdmec.w07150837.myguard.m5virusscan.utils.MD5Utils;

public class VirusScanSpeedActivity extends AppCompatActivity implements View.OnClickListener {

    protected static final int SCAN_BENGIN = 100;
    protected static final int SCANNING = 101;
    protected static final int SCAN_FINISH = 102;
    private int total;
    private int process;
    private TextView mProcessTV;
    private PackageManager pm;
    private boolean flag;
    private boolean isStop;
    private TextView mScanAppTV;
    private Button mCancleBtn;
    private ImageView mScanningIcon;
    private RotateAnimation rani;
    private ListView mScanListView;
    private ScanVirusAdapter adapter;
    private List<ScanAppInfo> mScanAppInfos = new ArrayList<ScanAppInfo>();
    private SharedPreferences mSP;
    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SCAN_BENGIN:
                    mScanAppTV.setText("初始化杀毒引擎中....");
                    break;
                case SCANNING:
                    ScanAppInfo info = (ScanAppInfo) msg.obj;
                    mScanAppTV.setText("正在扫描：" + info.appName);
                    int speed = msg.arg1;
                    mProcessTV.setText((speed * 100 / total) + "%");
                    mScanAppInfos.add(info);
                    adapter.notifyDataSetChanged();
                    mScanListView.setSelection(mScanAppInfos.size());
                    break;
                case SCAN_FINISH:
                    mScanAppTV.setText("扫描完成！");
                    mScanningIcon.clearAnimation();
                    mCancleBtn.setBackgroundResource(R.drawable.scan_complete);
                    saveScanTime();
                    break;

            }
        }

        private void saveScanTime() {

            SharedPreferences.Editor edit = mSP.edit();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String currentTime = sdf.format(new Date());
            currentTime = "上次查杀：" + currentTime;
            edit.putString("lastVirusScan", currentTime);
            edit.commit();

        }

    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_virus_scan_speed);
        pm = getPackageManager();
        mSP = getSharedPreferences("config", MODE_PRIVATE);
        initView();
        scanVirus();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void scanVirus() {

        flag = true;
        isStop = false;
        process = 0;
        mScanAppInfos.clear();
        new Thread() {
            @Override
            public void run() {
                super.run();
                Message msg = Message.obtain();
                msg.what = SCAN_BENGIN;
                List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
                total = installedPackages.size();
                for (PackageInfo info : installedPackages) {
                    if (!flag) {
                        isStop = true;
                        return;
                    }
                    String apkpath = info.applicationInfo.sourceDir;
                    String md5info = MD5Utils.getFileMd5(apkpath);
                    AntiVirusDao antiVirusDao = new AntiVirusDao(VirusScanSpeedActivity.this
                            .getApplicationContext());
                    String result = antiVirusDao.checkVirus(md5info);
                    msg = Message.obtain();
                    msg.what = SCANNING;
                    ScanAppInfo scanInfo = new ScanAppInfo();
                    if (result == null) {
                        scanInfo.description = "扫描安全";
                        scanInfo.isVirus = false;
                    } else {
                        scanInfo.description = result;
                        scanInfo.isVirus = true;
                    }
                    process++;
                    scanInfo.packagename = info.packageName;
                    scanInfo.appName = info.applicationInfo.loadLabel(pm).toString();
                    scanInfo.appicon = info.applicationInfo.loadIcon(pm);
                    msg.obj = scanInfo;
                    msg.arg1 = process;
                    mHandler.sendMessage(msg);

                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                msg = Message.obtain();
                msg.what = SCAN_FINISH;
                mHandler.sendMessage(msg);

            }


        }.start();


    }


    private void initView() {

        findViewById(R.id.r1_titlebar).setBackgroundColor(getResources().getColor(R.color
                .light_blue));
        ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
        ((TextView) findViewById(R.id.tv_title)).setText("病毒查杀进度");
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back);
        mProcessTV = (TextView) findViewById(R.id.tv_scanprocess);
        mScanAppTV = (TextView) findViewById(R.id.tv_sacnsapp);
        mCancleBtn = (Button) findViewById(R.id.btn_canclescan);
        mCancleBtn.setOnClickListener(this);
        mScanListView = (ListView) findViewById(R.id.lv_scansapps);
        adapter = new ScanVirusAdapter(mScanAppInfos, this);
        mScanListView.setAdapter(adapter);
        mScanningIcon = (ImageView) findViewById(R.id.imgv_scanningicon);
        startAnim();

    }

    private void startAnim() {
        if (rani == null) {
            rani = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation
                    .RELATIVE_TO_SELF, 0.5f);
        }
        rani.setRepeatCount(Animation.INFINITE);
        rani.setDuration(2000);
        mScanningIcon.startAnimation(rani);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.imgv_leftbtn:
                finish();
                break;
            case R.id.btn_canclescan:
                if (process == total & process > 0) {
                    finish();
                } else if (process > 0 & process < total & isStop == false) {
                    mScanningIcon.clearAnimation();
                    flag = false;
                    mCancleBtn.setBackgroundResource(R.drawable.restart_scan_btn);
                } else if (isStop) {
                    startAnim();
                    scanVirus();
                    mCancleBtn.setBackgroundResource(R.drawable.cancle_scan_btn_selector);
                }

                break;

        }

    }

    @Override
    protected void onDestroy() {
        flag = false;
        super.onDestroy();
    }
}