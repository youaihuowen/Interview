package com.wuyixiong.interview.event;

/**
 * Created by WUYIXIONG on 2017-5-23.
 */

public class ShareEvent {
    boolean goShare;

    public ShareEvent(boolean goShare) {
        this.goShare = goShare;
    }

    public ShareEvent() {
    }

    public boolean isGoShare() {
        return goShare;
    }

    public void setGoShare(boolean goShare) {
        this.goShare = goShare;
    }
}
