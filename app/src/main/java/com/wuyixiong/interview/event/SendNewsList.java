package com.wuyixiong.interview.event;

import com.wuyixiong.interview.entity.News;

import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2017-5-20.
 */

public class SendNewsList {
    private ArrayList<News> list;

    public SendNewsList(ArrayList<News> list) {
        this.list = list;
    }

    public ArrayList<News> getList() {
        return list;
    }

    public void setList(ArrayList<News> list) {
        this.list = list;
    }
}
