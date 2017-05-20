package com.wuyixiong.interview.utils;

import android.content.Context;
import android.util.Log;

import com.wuyixiong.interview.db.DBManager;
import com.wuyixiong.interview.entity.News;
import com.wuyixiong.interview.entity.NewsCollection;
import com.wuyixiong.interview.entity.Question;
import com.wuyixiong.interview.entity.QuestionCollection;
import com.wuyixiong.interview.entity.User;
import com.wuyixiong.interview.event.CollectEvent;
import com.wuyixiong.interview.event.IsCollection;
import com.wuyixiong.interview.event.SendError;
import com.wuyixiong.interview.event.SendNewsList;
import com.wuyixiong.interview.event.SendQuestionList;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by WUYIXIONG on 2017-5-16.
 */

public class CollectionOperate {
    private static CollectionOperate operate = null;

    private CollectionOperate() {
    }

    public static CollectionOperate getInstance() {
        if (operate == null) {
            operate = new CollectionOperate();
        }
        return operate;
    }

    /**
     * 收藏资讯方法
     *
     * @param newsCollectionId 本用户对应收藏表的objectId
     * @param news             资讯
     */
    public void addNewsCollection(String newsCollectionId, final News news) {

        NewsCollection collection = new NewsCollection();
        collection.setObjectId(newsCollectionId);
        BmobRelation relation = new BmobRelation();
        relation.add(news);
        collection.setLikes(relation);
        collection.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    EventBus.getDefault().post(new CollectEvent(true));
                    Log.i("tag", "------------------------收藏成功");

                } else {
                    EventBus.getDefault().post(new SendError("收藏失败", 2));
                    Log.i("tag", "------------------------+++" + e.toString());
                }
            }
        });
    }

    /**
     * 移除收藏的资讯
     *
     * @param newsCollectionId 本用户对应收藏表的objectId
     * @param news             资讯
     */
    public void removeNewsCollection(String newsCollectionId, final News news) {

        NewsCollection collection = new NewsCollection();
        collection.setObjectId(newsCollectionId);
        BmobRelation relation = new BmobRelation();
        relation.remove(news);
        collection.setLikes(relation);
        collection.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    EventBus.getDefault().post(new CollectEvent(false));
                    Log.i("tag", "------------------------取消成功");

                } else {
                    EventBus.getDefault().post(new SendError("取消失败", 3));
                    Log.i("tag", "------------------------+++" + e.toString());
                }
            }
        });
    }

    /**
     * 查询该用户的所有资讯收藏
     *
     * @param newsCollectionId
     */
    public void getAllNewsCollection(String newsCollectionId) {
        BmobQuery<News> query = new BmobQuery<>();
        NewsCollection collection = new NewsCollection();
        collection.setObjectId(newsCollectionId);
        query.addWhereRelatedTo("likes", new BmobPointer(collection));
        query.findObjects(new FindListener<News>() {
            @Override
            public void done(List<News> list, BmobException e) {
                if (e == null) {
                    EventBus.getDefault().post(new SendNewsList((ArrayList<News>) list));
                }
            }
        });

    }

    /**
     * 判断该条资讯是否被收藏
     *
     * @param newsCollectionId
     * @param newsId
     */
    public void isNewsCollection(String newsCollectionId, final String newsId) {
        BmobQuery<News> query = new BmobQuery<>();
        NewsCollection collection = new NewsCollection();
        collection.setObjectId(newsCollectionId);
        query.addWhereRelatedTo("likes", new BmobPointer(collection));
        query.addQueryKeys("objectId");
        query.findObjects(new FindListener<News>() {
            @Override
            public void done(List<News> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        if (newsId.equals(list.get(i).getObjectId())) {
                            EventBus.getDefault().post(new IsCollection(true));
                            break;
                        }
                        if (i == list.size() - 1) {
                            EventBus.getDefault().post(new IsCollection(false));
                        }
                    }
                }
            }
        });
    }

    /**
     * 收藏试题
     */
    public void addQuestionCollection(final Context context, String questionCollectionId, final Question question) {
        QuestionCollection collection = new QuestionCollection();
        collection.setObjectId(questionCollectionId);
        BmobRelation relation = new BmobRelation();
        relation.add(question);
        collection.setLikes(relation);
        collection.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    DBManager.getInstance(context).addQuestion(question);
                    EventBus.getDefault().post(new CollectEvent(true));
                    Log.i("tag", "------------------------收藏成功");

                } else {
                    EventBus.getDefault().post(new SendError("收藏失败", 2));
                    Log.i("tag", "------------------------+++" + e.toString());
                }
            }
        });
    }

    /**
     * 取消试题收藏
     *
     * @param questionCollectionId
     * @param question
     */
    public void removeQuestionCollection(final Context context, String questionCollectionId, final Question question) {

        QuestionCollection collection = new QuestionCollection();
        collection.setObjectId(questionCollectionId);
        BmobRelation relation = new BmobRelation();
        relation.remove(question);
        collection.setLikes(relation);
        collection.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    DBManager.getInstance(context).removeQuestion(question.getObjectId());
                    EventBus.getDefault().post(new CollectEvent(false));
                    Log.i("tag", "------------------------取消成功");

                } else {
                    EventBus.getDefault().post(new SendError("取消失败", 3));
                    Log.i("tag", "------------------------+++" + e.toString());
                }
            }
        });
    }

    /**
     * 查询该用户的所有试题收藏
     */
    public void getAllQuestionCollection(String QuestionCollectionId) {
        BmobQuery<Question> query = new BmobQuery<>();
        QuestionCollection collection = new QuestionCollection();
        collection.setObjectId(QuestionCollectionId);
        query.addWhereRelatedTo("likes", new BmobPointer(collection));
        query.findObjects(new FindListener<Question>() {
            @Override
            public void done(List<Question> list, BmobException e) {
                if (e == null){
                    EventBus.getDefault().post(new SendQuestionList((ArrayList<Question>) list));
                }
            }
        });

    }

    /**
     * 将所有试题收藏添加进数据库
     * @param QuestionCollectionId
     */
    public void AddAllQuestion(final Context context, String QuestionCollectionId) {
        BmobQuery<Question> query = new BmobQuery<>();
        QuestionCollection collection = new QuestionCollection();
        collection.setObjectId(QuestionCollectionId);
        query.addWhereRelatedTo("likes", new BmobPointer(collection));
        query.findObjects(new FindListener<Question>() {
            @Override
            public void done(List<Question> list, BmobException e) {
                DBManager.getInstance(context).addAllQuestion((ArrayList<Question>) list);
            }
        });
    }


}
