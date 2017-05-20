package com.wuyixiong.interview.event;

/**
 * Created by WUYIXIONG on 2017-5-20.
 */

public class InfoEvent {
    private String message;
    private int code;
    private String type;



    public InfoEvent() {
    }

    public InfoEvent(int code) {
        this.code = code;
    }

    public InfoEvent(String message, int code, String type) {
        this.message = message;
        this.code = code;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
