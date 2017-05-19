package com.wuyixiong.interview.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2017-5-18.
 */

public class QuestionList implements Serializable {
    private ArrayList<Question> list = new ArrayList<>();
    private int position;

    public QuestionList(ArrayList<Question> list,int position) {
        this.list = list;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ArrayList<Question> getList() {
        return list;
    }

    public void setList(ArrayList<Question> list) {
        this.list = list;
    }
}
