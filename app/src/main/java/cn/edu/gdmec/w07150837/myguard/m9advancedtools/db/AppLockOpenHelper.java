package cn.edu.gdmec.w07150837.myguard.m9advancedtools.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by weiruibo on 12/23/16.
 */

public class AppLockOpenHelper extends SQLiteOpenHelper {


    public AppLockOpenHelper(Context context) {
        super(context, "applock.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table applock(id integer primary key autoincrement," +
                "packagename varchar(20))");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
