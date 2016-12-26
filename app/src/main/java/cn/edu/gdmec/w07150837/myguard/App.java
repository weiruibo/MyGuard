package cn.edu.gdmec.w07150837.myguard;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by weiruibo on 12/19/16.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        correctSIM();
    }

    public void correctSIM() {

        SharedPreferences sp = getSharedPreferences("config",
                Context.MODE_PRIVATE);

        boolean protexting = sp.getBoolean("protecting", true);

        if (protexting) {

            String bindsim = sp.getString("sim", "");

            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            String realsim = tm.getSimSerialNumber();

            Log.d("App","当前手机Sim串号"+realsim);

            if (bindsim.equals(realsim)) {
                Log.i("App", "sim卡未发生变化,还是您的手机");
            } else {
                Log.i("App", "SIM卡变化了");

                String safenumber = sp.getString("safephone", "");

                if (!TextUtils.isEmpty(safenumber)) {

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(safenumber, null, "" +
                            "你的亲友手机的SIM卡已经被更换了!", null, null);

                }
            }

        }
    }
}
