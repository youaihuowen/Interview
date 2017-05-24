package com.wuyixiong.interview.event;

import com.wuyixiong.interview.entity.Comment;

import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2017-5-22.
 */

public class CommentEvent {
    private ArrayList<Comment> list;
    private int type = -1;//0为消息的评论 1为评论的子评论

    private boolean addComment = false;

    public CommentEvent() {
    }

    public CommentEvent(ArrayList<Comment> list, int type) {
        this.list = list;
        this.type = type;
    }

    public CommentEvent(boolean addComment) {
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

    public boolean isAddComment() {
        return addComment;
    }

    public void setAddComment(boolean addComment) {
        this.addComment = addComment;
    }
}
