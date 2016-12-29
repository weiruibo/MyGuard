package cn.edu.gdmec.w07150837.myguard.m10settings;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.gdmec.w07150837.myguard.R;
import cn.edu.gdmec.w07150837.myguard.m10settings.utils.SystemInfoUtils;
import cn.edu.gdmec.w07150837.myguard.m10settings.widget.SettingView;
import cn.edu.gdmec.w07150837.myguard.m9advancedtools.service.AppLockService;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, SettingView.
        OnCheckedStatusIsChanged {

    private SettingView mBlackNumSV;
    private SettingView mAppLockSV;
    private SharedPreferences mSP;
    private boolean running;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);
        mSP = getSharedPreferences("config", MODE_PRIVATE);
        initView();
    }

    private void initView() {
        findViewById(R.id.r1_titlebar).setBackgroundColor(
                getResources().getColor(R.color.bright_blue));
        ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
        ((TextView) findViewById(R.id.tv_title)).setText("设置中心");
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back);
        mBlackNumSV = (SettingView) findViewById(R.id.sv_blacknumber_set);
        mAppLockSV = (SettingView) findViewById(R.id.sv_applock_set);
        mBlackNumSV.setOnCheckedStatusIsChanged(this);
        mAppLockSV.setOnCheckedStatusIsChanged(this);

    }

    @Override
    protected void onStart() {
        running = SystemInfoUtils.isServiceRunning(this, "cn.edu.gdmec.w07150837.myguard.m9advancedtools.service.AppLockService");
        mAppLockSV.setChecked(mSP.getBoolean("AppLockStatus",false));
        mBlackNumSV.setChecked(mSP.getBoolean("BlackNumStatus", true));
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgv_leftbtn:
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(View view, boolean isChedked) {
        switch (view.getId()) {
            case R.id.sv_blacknumber_set:
                saveStatus("BlackNumStatus", isChedked);
                break;
            case R.id.sv_applock_set:
                saveStatus("AppLockStatus", isChedked);
                intent = new Intent(this, AppLockService.class);
                if (isChedked) {
                    startService(intent);
                } else {
                    stopService(intent);
                }
                break;
        }
    }

    private void saveStatus(String keyname, boolean inChecked) {
        if (!TextUtils.isEmpty(keyname)) {
            SharedPreferences.Editor edit = mSP.edit();
            edit.putBoolean(keyname, inChecked);
            edit.commit();
        }
    }
}

















































