package cn.edu.gdmec.w07150837.myguard.m7processmanager.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.view.accessibility.AccessibilityManager;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static cn.edu.gdmec.w07150837.myguard.R.id.info;

/**
 * Created by student on 16/12/22.
 */

public class SystemInfoUtils {


    /**
     * 判断一个服务是否处理运行状态
     *
     * @param context 上下文
     * @return
     */
    public static boolean isServiceRunning(Context context, String className) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> infos = am.getRunningServices(200);
        for (ActivityManager.RunningServiceInfo info : infos) {
            String serviceClassName = info.service.getClassName();
            if (className.equals(serviceClassName)) {
                return true;
            }
        }
        return false;

    }

    /**
     * 获取手机的总内存大小,单位byte
     *
     * @return
     */
    public static long getTotalMem() {
        try {
            FileInputStream fis = new FileInputStream(new File("/proc/meminfo"));
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String totalInfo = br.readLine();
            //MemTotal: 51300kb
            StringBuffer sb = new StringBuffer();
            for (char c : totalInfo.toCharArray()) {
                if (c >= '0' && c <= '9') {
                    sb.append(c);
                }
            }
            long bytesize = Long.parseLong(sb.toString()) * 1024;
            return bytesize;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long getAvailMem(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获取内存大小
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(outInfo);
        long availMem = outInfo.availMem;
        return availMem;
    }

    /**
     * 得到正在运行的进程的数量
     *
     * @param context
     * @return
     */
    public static int getRunningPocessCount(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = am.getRunningAppProcesses();
        int count = runningAppProcessInfos.size();
        return count;
    }


}
