package com.wuyixiong.interview.utils;

import android.icu.util.TimeUnit;
import android.util.Log;
import android.util.TimeUtils;
import android.widget.Toast;

import com.wuyixiong.interview.entity.Message;
import com.wuyixiong.interview.entity.News;
import com.wuyixiong.interview.event.SendError;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by WUYIXIONG on 2017-5-9.
 */

public class Query {
    private static Query query = null;

    private Query() {

    }

    public static Query getInstance() {
        if (query == null) {
            query = new Query();
        }
        return query;
    }


    /**
     * 查询资讯(有网)
     *
     * @return
     */
    public void queryNews(ArrayList<String> list) {
        BmobQuery<News> bmobQuery = new BmobQuery<News>();
        bmobQuery.addWhereContainedIn("category", list);
        bmobQuery.setLimit(15);//设置每次显示条数
        Boolean isCache = bmobQuery.hasCachedResult(News.class);
        if (isCache) {
            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        } else {
            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        }
        //设置缓存保留时间
        bmobQuery.setMaxCacheAge(java.util.concurrent.TimeUnit.DAYS.toMillis(1));
        bmobQuery.findObjects(new FindListener<News>() {
            @Override
            public void done(List<News> list, BmobException e) {
                if (e == null) {
                    EventBus.getDefault().post((ArrayList<News>) list);
                } else {
                    Log.i("tag", e.toString());
                }
            }
        });
    }

    /**
     * 查询资讯(没网)
     *
     * @param list
     */
    public void queryNewsWithouNetwork(ArrayList<String> list) {
        BmobQuery<News> bmobQuery = new BmobQuery<News>();
        bmobQuery.addWhereContainedIn("category", list);
        bmobQuery.setLimit(15);//设置每次显示条数
        Boolean isCache = bmobQuery.hasCachedResult(News.class);
        if (isCache) {
            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ONLY);
            bmobQuery.findObjects(new FindListener<News>() {
                @Override
                public void done(List<News> list, BmobException e) {
                    if (e == null) {
                        EventBus.getDefault().post((ArrayList<News>) list);
                    } else {
                        Log.i("tag", e.toString());
                    }
                }
            });
        } else {

        }
    }

    /**
     * 查询发表的面试题方法
     */
    public void queryMessage() {
        BmobQuery<Message> bmobQuery = new BmobQuery<>();
        bmobQuery.include("author");
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        //设置缓存保留时间
        bmobQuery.setMaxCacheAge(java.util.concurrent.TimeUnit.DAYS.toMillis(1));
        bmobQuery.findObjects(new FindListener<Message>() {
            @Override
            public void done(List<Message> list, BmobException e) {
                if (e == null){
                    EventBus.getDefault().post((ArrayList<Message>)list);
                }else {
                    SendError error = new SendError(e.getMessage(),1);
                    EventBus.getDefault().post(error);
                }
            }
        });
    }


}
