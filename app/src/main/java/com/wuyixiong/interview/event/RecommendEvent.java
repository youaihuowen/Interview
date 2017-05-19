package com.wuyixiong.interview.event;

import com.wuyixiong.interview.entity.Recommend;

import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2017-5-19.
 */

public class RecommendEvent {
    private ArrayList<Recommend> list;

    public RecommendEvent() {
    }

    public RecommendEvent(ArrayList<Recommend> list) {
        this.list = list;
    }

    public ArrayList<Recommend> getList() {
        return list;
    }

    public void setList(ArrayList<Recommend> list) {
        this.list = list;
    }
}
