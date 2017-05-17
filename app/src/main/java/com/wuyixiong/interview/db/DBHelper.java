package com.wuyixiong.interview.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by WUYIXIONG on 2016-10-27.
 */

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context) {
        super(context, "MyCollection.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table MyNews " +
                "(_id integer primary key autoincrement,objectId text,url text," +
                "title text,pic_url text,category text,data text)");
        db.execSQL("create table MyQuestion " +
                "(_id integer primary key autoincrement,objectId text,type text,title text," +
                "content text,url text,company text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
