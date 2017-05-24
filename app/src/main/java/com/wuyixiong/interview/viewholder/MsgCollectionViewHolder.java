package com.wuyixiong.interview.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuyixiong.interview.R;

/**
 * Created by WUYIXIONG on 2017-5-24.
 */

public class MsgCollectionViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout ll;
    public TextView tvName;
    public TextView tvTime;
    public ImageView ivPic;
    public MsgCollectionViewHolder(View itemView) {
        super(itemView);
        ll = (LinearLayout) itemView.findViewById(R.id.ll_item_msgcoll);
        tvName = (TextView) itemView.findViewById(R.id.tv_item_msgtitle);
        tvTime = (TextView) itemView.findViewById(R.id.tv_item_msgtime);
        ivPic = (ImageView) itemView.findViewById(R.id.iv_item_msgcoll);
    }
}
