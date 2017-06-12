package com.wuyixiong.interview.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuyixiong.interview.R;

/**
 * Created by WUYIXIONG on 2017-6-8.
 */

public class TestTypeViewHolder extends RecyclerView.ViewHolder {
    public ImageView iv;
    public TextView tv;

    public TestTypeViewHolder(View itemView) {
        super(itemView);
        iv = (ImageView) itemView.findViewById(R.id.iv_test_type);
        tv = (TextView) itemView.findViewById(R.id.tv_test_type);
    }
}
