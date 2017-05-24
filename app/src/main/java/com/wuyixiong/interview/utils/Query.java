package com.wuyixiong.interview.utils;

import android.content.Intent;
import android.icu.util.TimeUnit;
import android.util.Log;
import android.util.TimeUtils;
import android.widget.Toast;

import com.wuyixiong.interview.activity.DetailActivity;
import com.wuyixiong.interview.entity.Comment;
import com.wuyixiong.interview.entity.Message;
import com.wuyixiong.interview.entity.News;
import com.wuyixiong.interview.entity.Recommend;
import com.wuyixiong.interview.event.CommentEvent;
import com.wuyixiong.interview.event.RecommendEvent;
import com.wuyixiong.interview.event.SendError;
import com.wuyixiong.interview.event.SendNews;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
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
    public void queryMessage(int type) {
        //type 为0 先从缓存下载，Type为1 先从网络下载
        BmobQuery<Message> bmobQuery = new BmobQuery<>();
        bmobQuery.include("author");
        if (type == 0){
            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        }else {
            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }
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


    /**
     * 根据推荐类型查询内容
     * @param recommendType
     */
    public void queryRecommend(String recommendType){
        BmobQuery<Recommend>  query = new BmobQuery<>("recommend");
        query.addWhereEqualTo("recommendType",recommendType);
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        //设置缓存保留时间
        query.setMaxCacheAge(java.util.concurrent.TimeUnit.DAYS.toMillis(1));
        query.findObjects(new FindListener<Recommend>() {
            @Override
            public void done(List<Recommend> list, BmobException e) {
                if (e == null){
                    EventBus.getDefault().post(new RecommendEvent((ArrayList<Recommend>) list));
                }else {
                    EventBus.getDefault().post(new SendError("加载失败",4));
                }
            }
        });
    }

    /**
     * 查询消息的评论
     * @param messageId
     */
    public void queryComment(String messageId){
        BmobQuery<Comment> query = new BmobQuery<>();
        Message message = new Message();
        message.setObjectId(messageId);
        query.addWhereEqualTo("message",new BmobPointer(message));
        query.order("updateAt");
        query.include("author");
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if (e == null){
                    EventBus.getDefault().post(new CommentEvent((ArrayList<Comment>) list,0));
                    if (list.get(0).getAuthor() == null){
                        Log.i("tag", "-----------------"+"user 为 NULL");
                    }else {
                        Log.i("tag", "-----------------"+list.get(0).getAuthor().toString());
                        Log.i("tag", "-----------------"+list.get(1).getAuthor().toString());
                        Log.i("tag", "-----------------"+list.get(2).getAuthor().toString());
                    }
                }
            }
        });

    }

    /**
     * 查询评论的子评论
     * @param commentId
     */
    public void queryZiComment(String commentId){
        BmobQuery<Comment> query = new BmobQuery<>();
        Comment comment = new Comment();
        comment.setObjectId(commentId);
        query.addWhereRelatedTo("zi_comment",new BmobPointer(comment));
        query.include("user");
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if (e == null){
                    EventBus.getDefault().post(new CommentEvent((ArrayList<Comment>) list,1));
                }
            }
        });

    }


}
