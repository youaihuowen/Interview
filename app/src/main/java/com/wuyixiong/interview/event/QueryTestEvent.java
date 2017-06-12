package com.wuyixiong.interview.event;

import com.wuyixiong.interview.entity.Test;

import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2017-6-8.
 */

public class QueryTestEvent {
    private ArrayList<Test> list;
    private boolean done;

    public QueryTestEvent(ArrayList<Test> list, boolean done) {
        this.list = list;
        this.done = done;
    }

    public ArrayList<Test> getList() {
        return list;
    }

    public void setList(ArrayList<Test> list) {
        this.list = list;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
