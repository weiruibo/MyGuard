package cn.edu.gdmec.w07150837.myguard.m8trafficmonitor.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cn.edu.gdmec.w07150837.myguard.m8trafficmonitor.service.TrafficMonitoringService;
import cn.edu.gdmec.w07150837.myguard.m8trafficmonitor.utils.SystemInfoUtils;

/**
 * 监听开机的广播类,更新数据库,开启服务
 */
public class BootCompleteReciever extends BroadcastReceiver {
    public BootCompleteReciever() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //开机广播
        //判断流量监控服务是否开启,没有开启则开启
        if (!SystemInfoUtils.isServiceRunning(context, "cn.edu.gdmec.w07150837.myguard" +
                "m8trafficmonitor.service.TrafficMonitoringService")) {
            //开启服务
            Log.d("traffic service", "turn on");
            context.startService(new Intent(context, TrafficMonitoringService.class));
        }
    }
}
