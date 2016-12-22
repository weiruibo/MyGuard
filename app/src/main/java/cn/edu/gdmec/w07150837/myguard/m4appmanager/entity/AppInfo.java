package cn.edu.gdmec.w07150837.myguard.m4appmanager.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by weiruibo on 12/20/16.
 */

public class AppInfo {
    //包名
    public String packageName;
    //图标
    public Drawable icon;
    //名字
    public String appName;
    //路径
    public String apkPath;
    //大小
    public long appSize;
    //是否手机存储
    public boolean isInRomm;
    //是否用户应用
    public boolean isUserApp;
    //是否选中
    public boolean isSelected = false;

    public String getAppLocation(boolean isInRomm) {
        if (isInRomm) {
            return "手机内存";
        } else {
            return "外部存储";
        }

    }


}
