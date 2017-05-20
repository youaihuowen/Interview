package com.wuyixiong.interview.event;

import com.wuyixiong.interview.entity.Question;

import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2017-5-20.
 */

public class SendQuestionList {
    private ArrayList<Question> list;

    public SendQuestionList(ArrayList<Question> list) {
        this.list = list;
    }

    public ArrayList<Question> getList() {
        return list;
    }

    public void setList(ArrayList<Question> list) {
        this.list = list;
    }
}
