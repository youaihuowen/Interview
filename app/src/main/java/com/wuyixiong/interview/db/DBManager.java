package com.wuyixiong.interview.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wuyixiong.interview.entity.Question;

import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2017-5-17.
 */

public class DBManager {

    private Context mContext;

    private DBHelper dbHelper;

    private static final String NEWS_TABLE = "MyNews";
    private static final String QUESTION_TABLE = "MyQuestion";

    private static DBManager dbManager = null;


    private DBManager(Context context) {
        mContext = context;
        dbHelper = new DBHelper(mContext);
    }

    public static DBManager getInstance(Context context){
        if (dbManager == null){
            dbManager = new DBManager(context);
        }
        return dbManager;
    }

    //添加一条试题收藏
    public void addQuestion(Question question){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "insert into MyQuestion (objectId,type,title,content,url,company)values(?,?,?,?,?,?)";
        db.execSQL(sql,new Object[]{question.getObjectId(),question.getType(),question.getTitle(),
                question.getContent(),question.getUrl(),question.getCompany()});
        db.close();
    }

    //添加所有试题收藏
    public void addAllQuestion(ArrayList<Question> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "insert into MyQuestion (objectId,type,title,content,url,company)values(?,?,?,?,?,?)";
        for (Question question:list) {
            db.execSQL(sql,new Object[]{question.getObjectId(),question.getType(),question.getTitle(),
                    question.getContent(),question.getUrl(),question.getCompany()});
        }
        db.close();
    }
    //移除一条试题收藏
    public void removeQuestion(String questionId){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "delete from MyQuestion where objectId = ?";
        db.execSQL(sql,new Object[]{questionId});
        db.close();
    }
    //删除所有试题收藏
    public void removeAllQuestion(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "delete from MyQuestion";
        db.close();
    }
    //查询所有收藏试题的id
    public ArrayList<String> queryQuestionId(String type){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select objectId from MyQuestion where type = ?";
        Cursor c = db.rawQuery(sql,new String[]{type});
        if (c.getCount()<=0){
            db.close();
            return null;
        }else {
            c.moveToFirst();
            int index = c.getColumnIndexOrThrow("objectId");
            do {
                list.add(c.getString(index));
            } while (c.moveToNext());
        }
        db.close();
        return list;
    }


}
