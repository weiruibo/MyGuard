package cn.edu.gdmec.w07150837.myguard.m3communicationguard.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by weiruibo on 12/20/16.
 */

public class BlackNumberOpenHelper extends SQLiteOpenHelper {

    public BlackNumberOpenHelper(Context context) {
        super(context, "blackNumber.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table blacknumber" +
                " (id integer primary key autoincrement," +
                " number varchar(20), name varchar(255)," +
                " mode integet)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
