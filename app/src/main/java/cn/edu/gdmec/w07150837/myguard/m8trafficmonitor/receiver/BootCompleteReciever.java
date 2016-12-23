package cn.edu.gdmec.w07150837.myguard.m8trafficmonitor.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cn.edu.gdmec.w07150837.myguard.m8trafficmonitor.service.TrafficMonitoringService;
import cn.edu.gdmec.w07150837.myguard.m8trafficmonitor.utils.SystemInfoUtils;

public class BootCompleteReciever extends BroadcastReceiver {
    public BootCompleteReciever() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!SystemInfoUtils.isServiceRunning(context, "cn.edu.gdmec.w07150837.myguard" +
                ".m8trafficmonitor.servic.TrafficMonitoringService")) {
            Log.d("traffic service", "turn on");
            context.startService(new Intent(context, TrafficMonitoringService.class));
        }
    }
}
