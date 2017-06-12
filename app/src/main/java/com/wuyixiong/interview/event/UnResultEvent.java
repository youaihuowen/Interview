package com.wuyixiong.interview.event;

/**
 * Created by WUYIXIONG on 2017-6-8.
 */

public class UnResultEvent {
    private int index;

    public UnResultEvent(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
