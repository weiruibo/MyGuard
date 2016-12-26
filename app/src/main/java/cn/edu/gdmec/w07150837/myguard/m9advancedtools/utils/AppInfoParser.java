package cn.edu.gdmec.w07150837.myguard.m9advancedtools.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import cn.edu.gdmec.w07150837.myguard.m4appmanager.entity.AppInfo;

/**
 * Created by weiruibo on 12/23/16.
 */
/*工具类用来获取应用信息，此类重复*/
public class AppInfoParser {
  public static List<AppInfo> getAppInfos(Context context){
      //得到一个java保证的 包管理器
      PackageManager pm=context.getPackageManager();
      List<PackageInfo> packInfos=pm.getInstalledPackages(0);
      List<AppInfo> appinfos=new ArrayList<AppInfo>();
      for(PackageInfo packInfo:packInfos){
          AppInfo appinfo=new AppInfo();
          String packname=packInfo.packageName;
          appinfo.packageName=packname;
          Drawable icon=packInfo.applicationInfo.loadIcon(pm);
          appinfo.icon=icon;
          String appname=packInfo.applicationInfo.loadLabel(pm).toString();
          appinfo.appName=appname;
          //应用程序apk包的路径
          String apkpath=packInfo.applicationInfo.sourceDir;
          appinfo.apkPath=apkpath;
          appinfos.add(appinfo);
          appinfo=null;
      }
      return appinfos;
  }
}
