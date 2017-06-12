package com.wuyixiong.interview.event;

import com.wuyixiong.interview.entity.Comment;

import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2017-5-22.
 */

public class CommentEvent {
    private ArrayList<Comment> list;
    private int type ;//1为查询成功 -1为查询失败
    private int addComment;//1为添加成功，-1为添加失败

    public CommentEvent() {
    }

    public CommentEvent(ArrayList<Comment> list, int type) {
        this.list = list;
        this.type = type;
    }

    public CommentEvent(ArrayList<Comment> list, int type, int addComment) {
        this.list = list;
        this.type = type;
        this.addComment = addComment;
    }

    public ArrayList<Comment> getList() {
        return list;
    }

    public void setList(ArrayList<Comment> list) {
        this.list = list;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAddComment() {
        return addComment;
    }

    public void setAddComment(int addComment) {
        this.addComment = addComment;
    }
}
