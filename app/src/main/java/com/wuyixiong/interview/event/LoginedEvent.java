package com.wuyixiong.interview.event;

/**
 * Created by WUYIXIONG on 2017-5-18.
 */

public class LoginedEvent {
    private boolean logined;

    public LoginedEvent(boolean isLogined) {
        this.logined = isLogined;
    }

    public boolean isLogined() {
        return logined;
    }

    public void setLogined(boolean logined) {
        this.logined = logined;
    }
}
