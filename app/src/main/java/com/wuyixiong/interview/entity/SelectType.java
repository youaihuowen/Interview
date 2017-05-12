package com.wuyixiong.interview.entity;

/**
 * Created by WUYIXIONG on 2017-5-11.
 */

public class SelectType {
    private String type;
    private boolean isSelected =false;

    public SelectType(String type, boolean isSelected) {
        this.type = type;
        this.isSelected = isSelected;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
