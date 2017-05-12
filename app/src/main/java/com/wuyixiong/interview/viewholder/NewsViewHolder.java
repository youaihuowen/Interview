package com.wuyixiong.interview.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuyixiong.interview.R;

/**
 * Created by WUYIXIONG on 2017-5-7.
 */

public class NewsViewHolder extends RecyclerView.ViewHolder {

    public TextView newsTitle;
    public TextView newsTime;
    public ImageView newsImage;
    public LinearLayout ll;

    private int viewType = 0;

    public NewsViewHolder(View itemView) {
        super(itemView);
            ll = (LinearLayout) itemView.findViewById(R.id.ll_item_news);
            newsImage = (ImageView) itemView.findViewById(R.id.iv_item_newsimage);
            newsTitle = (TextView) itemView.findViewById(R.id.tv_item_newsTitle);
            newsTime = (TextView) itemView.findViewById(R.id.tv_item_newstime);
    }

    public int getViewType() {
        return viewType;
    }
}
