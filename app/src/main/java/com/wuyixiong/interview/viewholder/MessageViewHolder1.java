package com.wuyixiong.interview.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuyixiong.interview.R;

/**
 * Created by WUYIXIONG on 2017-5-15.
 */

public class MessageViewHolder1 extends RecyclerView.ViewHolder {
    //头中包含控件
    public LinearLayout Author;
    public ImageView icon;
    public TextView authorName;
    public TextView company;
    public TextView type;
    //底部包含控件
    public TextView date;
    public ImageView comment;
    //中间包含的控件
    public LinearLayout contents;
    public TextView message;
    public ImageView pic1;

    public MessageViewHolder1(View itemView) {
        super(itemView);
        Author = (LinearLayout) itemView.findViewById(R.id.ll_message_head);
        icon = (ImageView) itemView.findViewById(R.id.iv_message_head_icon);
        authorName = (TextView) itemView.findViewById(R.id.tv_message_head_name);
        company = (TextView) itemView.findViewById(R.id.tv_message_head_company);
        type = (TextView) itemView.findViewById(R.id.tv_message_head_type);
        date = (TextView) itemView.findViewById(R.id.tv_message_bottom_date);
        comment = (ImageView) itemView.findViewById(R.id.iv_message_bottom_comment);

        contents = (LinearLayout) itemView.findViewById(R.id.ll_message_content1);
        message = (TextView) itemView.findViewById(R.id.tv_message_content_type1);
        pic1 = (ImageView) itemView.findViewById(R.id.iv_message_pic_type1);

    }
}
