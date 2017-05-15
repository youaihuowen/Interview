package com.wuyixiong.interview.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by WUYIXIONG on 2017-5-13.
 */

public class Question extends BmobObject {
    private String url;
    private String type;
    private String title;
    private String content;
    private String company;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
