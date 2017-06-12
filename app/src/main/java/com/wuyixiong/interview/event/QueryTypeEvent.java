package com.wuyixiong.interview.event;

import com.wuyixiong.interview.entity.Type;

import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2017-6-8.
 */

public class QueryTypeEvent {
    private ArrayList<Type> list;
    private boolean done;

    public QueryTypeEvent(ArrayList<Type> list, boolean done) {
        this.list = list;
        this.done = done;
    }

    public ArrayList<Type> getList() {
        return list;
    }

    public void setList(ArrayList<Type> list) {
        this.list = list;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
