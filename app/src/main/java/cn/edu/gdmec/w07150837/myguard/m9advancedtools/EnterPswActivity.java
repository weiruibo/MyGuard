package cn.edu.gdmec.w07150837.myguard.m9advancedtools;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import cn.edu.gdmec.w07150837.myguard.R;
import cn.edu.gdmec.w07150837.myguard.m2theftguard.utils.MD5Utils;

public class EnterPswActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mAppIcon;
    private TextView mAppNameTV;
    private EditText mPswET;
    private ImageView mGoImgV;
    private LinearLayout mEnterPswLL;
    private SharedPreferences sp;
    private String password;
    private String packagename;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_enter_psw);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        password = sp.getString("PhoneAntiTheftPWD", null);
        Intent intent = getIntent();
        packagename = intent.getStringExtra("packagename");
        PackageManager pm = getPackageManager();
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //initview();
        try {
            mAppIcon.setImageDrawable(pm.getApplicationInfo(packagename, 0).loadIcon(pm));
            mAppNameTV.setText(pm.getApplicationInfo(packagename, 0).loadLabel(pm).toString());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    //！！！！！！！！！！！
    /*初始化控件*/
    /*private void initview() {
        mAppIcon = (ImageView) findViewById(R.id.imgv_appicon_enterpsw);
        mAppNameTV = (TextView) findViewById(R.id.tv_appname_enterpse);
        mPswET = (EditText) findViewById(R.id.et_psw_enterpsw);
        mGoImgV = (ImageView) findViewById(R.id.imgv_go_enterpsw);
        mEnterPswLL = (LinearLayout) findViewById(R.id.ll_enterpsw);
        mGoImgV.setOnClickListener(this);
    }*/

    public void onClick(View v) {
        /*switch (v.getId()) {
            case R.id.imgv_go_enterpsw:
                //比较密码
                String inputpsw = mPswET.getText().toString().trim();
                return;
        }else{
            if (!TextUtils.isEmpty(password)) {
                if (MD5Utils.encode(inputpsw).equals(password)) {
                    //发送自定义的广播消息
                    Intent intent = new Intent();
                    intent.setAction("cn.itcast.mobliesafe.applock");
                    intent.putExtra("packagename", packagename);
                    sendBroadcast(intent);
                    finish();
                } else {
                    startAnim();
                    Toast.makeText(this, "密码不正确", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
        break;*/

    }
    private void startAnim(){
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.shake);
        mEnterPswLL.startAnimation(animation);

}





}
