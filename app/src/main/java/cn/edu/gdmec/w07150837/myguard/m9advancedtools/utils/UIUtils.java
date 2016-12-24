package cn.edu.gdmec.w07150837.myguard.m9advancedtools.utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by weiruibo on 12/23/16.
 */

public class UIUtils {
    public static void showToast(final Activity context,final String msg){
        if ("main".equals(Thread.currentThread().getName())){
            Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
        }else {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
