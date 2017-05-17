package com.wuyixiong.interview.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by WUYIXIONG on 2017-5-17.
 */

public class DBManager {

    private Context mContext;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    private static DBManager dbManager = null;


    private DBManager(Context context) {
        mContext = context;
        dbHelper = new DBHelper(mContext);
        db = dbHelper.getWritableDatabase();
    }

    public static DBManager getInstance(Context context){
        if (dbManager == null){
            dbManager = new DBManager(context);
        }
        return dbManager;
    }




}
