package com.wuyixiong.interview.view;

import android.content.Context;
import android.widget.ImageView;

import com.github.why168.modle.BannerInfo;

import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2017-5-7.
 */

public interface OnLoadImageViewListener {
    /**
     * create image
     *
     * @param context context
     * @return image
     */
    ImageView createImageView(Context context);

    /**
     * image load
     *
     * @param imageView ImageView
     * @param parameter String    可以为一个文件路径、uri或者url
     *                  Uri   uri类型
     *                  File  文件
     *                  Integer   资源Id,R.drawable.xxx或者R.mipmap.xxx
     *                  byte[]    类型
     *                  T 自定义类型
     */
    void onLoadImageView(ImageView imageView, Object parameter);
}

