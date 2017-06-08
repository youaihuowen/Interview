package com.wuyixiong.interview.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by WUYIXIONG on 2017-6-7.
 */

public class Interest extends BmobObject {
    private String userId;
    private boolean Html;
    private boolean ios;
    private boolean Android;
    private boolean C;
    private boolean SQL;
    private boolean Linux;
    private boolean Python;
    private boolean PHP;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isHtml() {
        return Html;
    }

    public void setHtml(boolean html) {
        Html = html;
    }

    public boolean isIos() {
        return ios;
    }

    public void setIos(boolean ios) {
        this.ios = ios;
    }

    public boolean isAndroid() {
        return Android;
    }

    public void setAndroid(boolean android) {
        Android = android;
    }

    public boolean isC() {
        return C;
    }

    public void setC(boolean c) {
        C = c;
    }

    public boolean isSQL() {
        return SQL;
    }

    public void setSQL(boolean SQL) {
        this.SQL = SQL;
    }

    public boolean isLinux() {
        return Linux;
    }

    public void setLinux(boolean linux) {
        Linux = linux;
    }

    public boolean isPython() {
        return Python;
    }

    public void setPython(boolean python) {
        Python = python;
    }

    public boolean isPHP() {
        return PHP;
    }

    public void setPHP(boolean PHP) {
        this.PHP = PHP;
    }
}
