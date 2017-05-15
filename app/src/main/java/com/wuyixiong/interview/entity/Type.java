package com.wuyixiong.interview.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by WUYIXIONG on 2017-5-12.
 */

public class Type extends BmobObject {
    String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
