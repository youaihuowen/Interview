package com.wuyixiong.interview.event;

/**
 * Created by WUYIXIONG on 2017-5-17.
 */

public class IsCollection {
    private boolean collected;

    public IsCollection(boolean collected) {
        this.collected = collected;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }
}
