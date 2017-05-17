package com.wuyixiong.interview.event;

/**
 * Created by WUYIXIONG on 2017-5-16.
 */

public class CollectEvent {
    private boolean isCollected;
    private String newsLikesId;

    public String getNewsLikesId() {
        return newsLikesId;
    }

    public void setNewsLikesId(String newsLikesId) {
        this.newsLikesId = newsLikesId;
    }

    public CollectEvent(boolean isCollected) {
        this.isCollected = isCollected;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }
}
