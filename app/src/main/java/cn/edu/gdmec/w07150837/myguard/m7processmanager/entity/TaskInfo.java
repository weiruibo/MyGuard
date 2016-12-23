package cn.edu.gdmec.w07150837.myguard.m7processmanager.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by student on 16/12/22.
 */

public class TaskInfo {

    //真该运行的App信息
    public String appName;
    public long appMemory;
    /*用来标记app是否被选中*/
    public boolean isChecked;
    public Drawable appIcon;
    public boolean isUserApp;
    public String packageName;
}
