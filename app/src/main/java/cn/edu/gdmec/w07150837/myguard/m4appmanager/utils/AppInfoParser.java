package cn.edu.gdmec.w07150837.myguard.m4appmanager.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.w07150837.myguard.m4appmanager.entity.AppInfo;

/**
 * Created by weiruibo on 12/20/16.
 */

public class AppInfoParser {
    /**
     * 获取手机里面的所有应用程序
     *
     * @param context 上下文
     */

    public static List<AppInfo> getAppInfo(Context context) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
        List<AppInfo> appinfos = new ArrayList<AppInfo>();
        for (PackageInfo packageInfo : packageInfos) {
            AppInfo appInfo = new AppInfo();
            String packname = packageInfo.packageName;
            appInfo.packageName = packname;

            Drawable icon = packageInfo.applicationInfo.loadIcon(pm);
            appInfo.icon = icon;

            String appname = packageInfo.applicationInfo.loadLabel(pm).toString();
            appInfo.appName = appname;
            //应用程序apk包的路径
            String apkpath = packageInfo.applicationInfo.sourceDir;
            appInfo.apkPath = apkpath;

            File file = new File(apkpath);
            long appsize = file.length();
            appInfo.appSize = appsize;

            int flags = packageInfo.applicationInfo.flags;
            if ((ApplicationInfo.FLAG_EXTERNAL_STORAGE & flags) != 0) {
                //外部存储
                appInfo.isInRomm = false;
            } else {
                //手机存储
                appInfo.isInRomm = true;
            }
            if ((ApplicationInfo.FLAG_SYSTEM & flags) != 0) {
                //系统应用
                appInfo.isUserApp = false;
            } else {
                //用户应用
                appInfo.isUserApp = true;
            }
            appinfos.add(appInfo);
            appInfo = null;
        }
        return appinfos;
    }
}
