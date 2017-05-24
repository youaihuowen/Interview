package com.wuyixiong.interview.event;

import com.wuyixiong.interview.entity.Message;

import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2017-5-24.
 */

public class SendMessageList {
    ArrayList<Message> list;

    public SendMessageList(ArrayList<Message> list) {
        this.list = list;
    }

    public ArrayList<Message> getList() {
        return list;
    }

    public void setList(ArrayList<Message> list) {
        this.list = list;
    }
}
