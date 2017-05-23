package com.wuyixiong.interview.event;

import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2017-5-23.
 */

public class PublishEvent {
    private int sucess; //0 为上传图片成功  1为上传信息成功 -1为失败
    private ArrayList<String> list;
    private String err = null;
    private int i;

    public PublishEvent(int sucess, ArrayList<String> list) {
        this.sucess = sucess;
        this.list = list;
    }

    public PublishEvent(String err,int sucess) {
        this.sucess = sucess;
        this.err = err;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getSucess() {
        return sucess;
    }

    public void setSucess(int sucess) {
        this.sucess = sucess;
    }
}
