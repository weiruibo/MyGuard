package cn.edu.gdmec.w07150837.myguard.m7processmanager.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AutoKillProcessService extends Service {
    public AutoKillProcessService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
