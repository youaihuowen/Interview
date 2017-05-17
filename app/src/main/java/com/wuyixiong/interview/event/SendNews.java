package com.wuyixiong.interview.event;

import android.os.Parcelable;

import com.wuyixiong.interview.entity.News;

import java.io.Serializable;

/**
 * Created by WUYIXIONG on 2017-5-9.
 */

public class SendNews implements Serializable {
    private String url;

    private News news;

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
