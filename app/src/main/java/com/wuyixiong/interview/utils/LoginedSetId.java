package com.wuyixiong.interview.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.wuyixiong.interview.entity.Interest;
import com.wuyixiong.interview.entity.NewsCollection;
import com.wuyixiong.interview.entity.QuestionCollection;
import com.wuyixiong.interview.entity.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by WUYIXIONG on 2017-5-16.
 */

public class LoginedSetId {
    private Context mContext;

    public LoginedSetId(Context mContext) {
        this.mContext = mContext;
    }


    /**
     * 注册时设置收藏表本用户数据
     * @param userid
     */
    public void setCollectionTable(final String userid){
        NewsCollection newsCollection = new NewsCollection();
        newsCollection.setUserid(userid);
        newsCollection.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    //获取资讯收藏的id
                    BmobQuery<NewsCollection> query = new BmobQuery<>();
                    query.addQueryKeys("objectId");
                    query.addWhereEqualTo("userid",userid);
                    query.findObjects(new FindListener<NewsCollection>() {
                        @Override
                        public void done(List<NewsCollection> list, BmobException e) {
                            if (e==null){
                                SharedPreferences shared =mContext.getSharedPreferences("collectionId",MODE_PRIVATE);
                                SharedPreferences.Editor editor = shared.edit();
                                editor.putString("newsCollectionId",list.get(0).getObjectId());
                            }else {
                                Log.i("tag", "---------------------"+"获取newsCollectionId失败");
                            }
                        }
                    });
                    Log.i("tag", "-----------"+"注册时设置收藏表成功");
                }else {
                    Log.i("tag", "-----------" + "注册时设置收藏表失败");
                }

            }
        });

        QuestionCollection questionCollection = new QuestionCollection();
        questionCollection.setUserid(userid);
        questionCollection.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    //获取试题收藏的id
                    BmobQuery<QuestionCollection> query1 = new BmobQuery<>();
                    query1.addQueryKeys("objectId");
                    query1.addWhereEqualTo("userid",userid);
                    query1.findObjects(new FindListener<QuestionCollection>() {
                        @Override
                        public void done(List<QuestionCollection> list, BmobException e) {
                            if (e==null){
                                SharedPreferences shared =mContext.getSharedPreferences("collectionId",MODE_PRIVATE);
                                SharedPreferences.Editor editor = shared.edit();
                                editor.putString("questionCollectionId",list.get(0).getObjectId());
                            }else {
                                Log.i("tag", "---------------------"+"获取questionCollectionId失败");

                            }
                        }
                    });
                    Log.i("tag", "-----------"+"注册时设置收藏表成功");
                }else {
                    Log.i("tag", "-----------" + "注册时设置收藏表失败");
                }
            }
        });

        Interest interest = new Interest();
        interest.setUserId(userid);
        interest.setC(false);
        interest.setAndroid(true);
        interest.setHtml(true);
        interest.setIos(false);
        interest.setLinux(false);
        interest.setPHP(false);
        interest.setPython(false);
        interest.setSQL(false);
        interest.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null){
                    Log.i("tag", "-----------"+"注册时创建关注信息成功");

                    BmobQuery<Interest> query2 = new BmobQuery<Interest>();
                    query2.addWhereEqualTo("userId",userid);
                    query2.findObjects(new FindListener<Interest>() {
                        @Override
                        public void done(List<Interest> list, BmobException e) {
                            if (e == null && list.size()>0){
                                setInterest(list.get(0));
                            }else {
                                Log.i("tag", "-----------"+"注册时将关注写入sharedpreference失败");
                            };
                        }
                    });
                }else {
                    Log.i("tag", "-----------"+"注册时创建关注信息失败");
                }
            }
        });


    }

    /**
     * 登录时获取收藏表的id存入SharedPreference中
     * @param userid
     */
    public void getCollectionId(String userid){
        //获取资讯收藏的id
        BmobQuery<NewsCollection> query = new BmobQuery<>();
        query.addQueryKeys("objectId");
        query.addWhereEqualTo("userid",userid);
        query.findObjects(new FindListener<NewsCollection>() {
            @Override
            public void done(List<NewsCollection> list, BmobException e) {
                if (e==null){
                    SharedPreferences shared =mContext.getSharedPreferences("collectionId",MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString("newsCollectionId",list.get(0).getObjectId());
                    editor.commit();

                }else {
                    Log.i("tag", "---------------------"+"获取newsCollectionId失败");
                }
            }
        });

        //获取试题收藏的id
        BmobQuery<QuestionCollection> query1 = new BmobQuery<>();
        query1.addQueryKeys("objectId");
        query1.addWhereEqualTo("userid",userid);
        query1.findObjects(new FindListener<QuestionCollection>() {
            @Override
            public void done(List<QuestionCollection> list, BmobException e) {
                if (e==null){
                    SharedPreferences shared =mContext.getSharedPreferences("collectionId",MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString("questionCollectionId",list.get(0).getObjectId());
                    editor.commit();
                    CollectionOperate.getInstance().AddAllQuestion(mContext,list.get(0).getObjectId());
                }else {
                    Log.i("tag", "---------------------"+"获取questionCollectionId失败");

                }
            }
        });

        BmobQuery<Interest> query2 = new BmobQuery<Interest>();
        query2.addWhereEqualTo("userId",userid);
        query2.findObjects(new FindListener<Interest>() {
            @Override
            public void done(List<Interest> list, BmobException e) {
                if (e == null && list.size()>0){
                    setInterest(list.get(0));
                    Log.i("tag", "-----------"+"登录时将关注写入sharedpreference成功");

                }else {
                    Log.i("tag", "-----------"+"登录时将关注写入sharedpreference失败");
                };
            }
        });

    }

    /**
     * 将关注的类型存入sharedpreference中
     * @param interest
     */
    public void setInterest(Interest interest){
        SharedPreferences shared =mContext.getSharedPreferences("interest",MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean("Android",interest.isAndroid());
        editor.putBoolean("ios",interest.isIos());
        editor.putBoolean("Html",interest.isHtml());
        editor.putBoolean("C++",interest.isC());
        editor.putBoolean("PHP",interest.isPHP());
        editor.putBoolean("SQL",interest.isSQL());
        editor.putBoolean("Linux",interest.isLinux());
        editor.putBoolean("Python",interest.isPython());
        editor.commit();
    }
}
