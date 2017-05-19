package com.wuyixiong.interview.viewholder;

import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuyixiong.interview.R;

/**
 * Created by WUYIXIONG on 2017-5-19.
 */

public class RecommendViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout ll;
    public TextView title;
    public TextView likeOrDate;
    public ImageView iv;
    public RecommendViewHolder(View itemView) {
        super(itemView);
        ll = (LinearLayout) itemView.findViewById(R.id.ll_item_recommend);
        title = (TextView) itemView.findViewById(R.id.tv_item_recommendtitle);
        likeOrDate = (TextView) itemView.findViewById(R.id.tv_item_likeordate);
        iv = (ImageView) itemView.findViewById(R.id.iv_item_interview);
    }
}
