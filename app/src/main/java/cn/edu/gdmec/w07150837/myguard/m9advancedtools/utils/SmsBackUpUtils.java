package cn.edu.gdmec.w07150837.myguard.m9advancedtools.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by weiruibo on 12/23/16.
 */
/*短信的工具类，提供短信备份API*/
/*public class SmsBackUpUtils {
    *//*定义的一个接口，用作回调*//*
    public interface BackupStatusCallBack{
        *//*在短信备份之前调用的办法*//*
    public void beforeSmsBackup(int size);

        private boolean flag=true;
        public void setFlag(boolean flag){
            this.flag=flag;
        }
        public boolean backupSms(Context context,BackupStatusCallBack callback)
                throws FileNotFoundException,IllegalStateException,IOException{
            XmlSerializer serializer= Xml.newSerializer();
            File sdDir= Environment.getExternalStorageDirectory();
            long freesize=sdDir.getFreeSpace();
            if(Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)&&freesize>1041*1041){
                File file=new File(Environment.getExternalStorageDirectory(),"backup.xml");
                FileOutputStream os=new FileOutputStream(file);
                //初始化xml文件的序列化器
                serializer.setOutput(os,"utf-8");
                //写xml文件的头
                serializer.startDocument("utf-8",true);
                //写根节点
                ContentResolver resolver=context.getContentResolver();
                Uri uri=Uri.parse("content://sms/");
                Cursor cursor =resolver.query(uri,new String[]{
                        "address","body","type","date"},null,null,null);
                //得到总的条目的个数
                int size=cursor.getCount();
                //设置进度的总大小
                callback.beforeSmsBackup(size);
                serializer.startTag(null,"sms");
                serializer.startTag(null,"body");
                //可能会有乱码问题需要处理，如果出现乱码会导致备份失败
                try {
                    String bodyencpyt=Crypto.encrypt("123",cursor.getString(1));
                    serializer.text(bodyencpyt);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    serializer.text("读取短信失败");
                }
                serializer.endTag(null,"body");
                serializer.startTag(null,"address");
                serializer.text(cursor.getString(0));
                serializer.endTag(null,"address");
                serializer.startTag(null,"type");
                serializer.text(cursor.getString(2));
                serializer.endTag(null,"type");
                serializer.startTag(null,"date");
                serializer.text(cursor.getString(3));
                serializer.endTag(null,"date");
                serializer.endTag(null,"sms");
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //设置进度条对话框的进度

                process++;
                callback.onSmsBackup(process);
            }
            cursor.close();
            serializer.endTag(null,"smss");
            serializer.endDocument();
            os.flush();
            os.close();
            return flag;
            }else{
           throw  new IllegalStateException("sd卡不存在或者空间不足");
        }
    }
}*/
