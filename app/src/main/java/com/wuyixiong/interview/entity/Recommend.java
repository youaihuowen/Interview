package com.wuyixiong.interview.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by WUYIXIONG on 2017-5-19.
 */

public class Recommend extends BmobObject {
    private String url;
    private String title;
    private String recommendType;
    private String pic_url;
    private int likeNum;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRecommendType() {
        return recommendType;
    }

    public void setRecommendType(String recommendType) {
        this.recommendType = recommendType;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }
}
