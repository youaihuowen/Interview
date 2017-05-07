package com.wuyixiong.interview.entity;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by WUYIXIONG on 2017-4-28.
 */

public class MineItem {
    private int imageID;
    private String text;
    private int arrowID;
    private int type = 1;

    public MineItem(int imageID, String text, int arrowID) {
        this.imageID = imageID;
        this.text = text;
        this.arrowID = arrowID;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getArrowID() {
        return arrowID;
    }

    public void setArrowID(int arrowID) {
        this.arrowID = arrowID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
