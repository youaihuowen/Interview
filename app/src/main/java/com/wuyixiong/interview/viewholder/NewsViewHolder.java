package com.wuyixiong.interview.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuyixiong.interview.R;

/**
 * Created by WUYIXIONG on 2017-5-7.
 */

public class NewsViewHolder extends RecyclerView.ViewHolder {

    private TextView newsTitle;
    private TextView newsTime;
    private ImageView newsImage;

    private int viewType = 0;

    public NewsViewHolder(View itemView) {
        super(itemView);
            newsImage = (ImageView) itemView.findViewById(R.id.iv_item_newsimage);
            newsTitle = (TextView) itemView.findViewById(R.id.tv_item_newsTitle);
            newsTime = (TextView) itemView.findViewById(R.id.tv_item_newstime);
    }

    public int getViewType() {
        return viewType;
    }
}
