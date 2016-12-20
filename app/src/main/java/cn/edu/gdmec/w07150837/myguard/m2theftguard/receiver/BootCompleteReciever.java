package cn.edu.gdmec.w07150837.myguard.m2theftguard.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.edu.gdmec.w07150837.myguard.App;

public class BootCompleteReciever extends BroadcastReceiver {

    private static final String TAG = BootCompleteReciever.class.getSimpleName();


    @Override
    public void onReceive(Context context, Intent intent) {
        ((App) context.getApplicationContext()).correctSIM();
    }
}
