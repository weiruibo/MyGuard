package cn.edu.gdmec.w07150837.myguard.m9advancedtools.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;

import java.util.List;

import cn.edu.gdmec.w07150837.myguard.m9advancedtools.EnterPswActivity;
import cn.edu.gdmec.w07150837.myguard.m9advancedtools.db.dao.AppLockDao;

public class AppLockService extends Service {

    private boolean flag = false;
    private AppLockDao dao;
    private Uri uri = Uri.parse("content://cn.edu.gdmec.w07150837.myguard.applock");
    private List<String> packagenames;
    private Intent intent;
    private ActivityManager am;
    private List<ActivityManager.RunningTaskInfo> taskInfos;
    private ActivityManager.RunningTaskInfo taskInfo;
    private String packagename;
    private String tempStopProtectPackname;
    private AppLockReceiver receiver;
    private MyObserver observer;

    public AppLockService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {


        dao = new AppLockDao(this);
        observer = new MyObserver(new Handler());
        getContentResolver().registerContentObserver(uri, true, observer);
        packagenames = dao.findAll();
        receiver = new AppLockReceiver();
        IntentFilter filter = new IntentFilter("cn.edu.gdmec.w07150837.myguard.applock");
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(receiver, filter);
        intent = new Intent(AppLockService.this, EnterPswActivity.class);
        am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        startApplockService();
        super.onCreate();
    }

    private void startApplockService() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                flag = true;
                while (flag) {
                    taskInfos = am.getRunningTasks(1);
                    taskInfo = taskInfos.get(0);
                    packagename = taskInfo.topActivity.getPackageName();
                    if (packagenames.contains(packagename)) {
                        if (!packagename.equals(tempStopProtectPackname)) {
                            intent.putExtra("packagename", packagename);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();


    }

    class AppLockReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("cn.edu.gdmec.w07150837.myguard.applock".equals(intent.getAction())) {
                tempStopProtectPackname = intent.getStringExtra("packagename");
            } else if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
                tempStopProtectPackname = null;
                flag = false;
            } else if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
                if (flag == false) {
                    startApplockService();
                }
            }

        }
    }

    class MyObserver extends ContentObserver {

        public MyObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            packagenames = dao.findAll();
            super.onChange(selfChange);
        }
    }

    @Override
    public void onDestroy() {
        flag = false;
        unregisterReceiver(receiver);
        receiver = null;
        getContentResolver().unregisterContentObserver(observer);
        observer = null;
        super.onDestroy();
    }
}
