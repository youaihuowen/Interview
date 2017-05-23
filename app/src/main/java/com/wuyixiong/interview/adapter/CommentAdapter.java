package com.wuyixiong.interview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wuyixiong.interview.R;
import com.wuyixiong.interview.entity.Comment;
import com.wuyixiong.interview.view.CircleImageView;

import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2017-5-22.
 */

public class CommentAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Comment> data = new ArrayList<>() ;

    public CommentAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<Comment> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CviewHolder holder = null;
        if (view == null) {
            view= LayoutInflater.from(context).inflate(R.layout.item_comment, null);
            holder = new CviewHolder(view);
            view.setTag(holder);
        }else {
            holder = (CviewHolder) view.getTag();
        }
        ImageLoader.getInstance().displayImage(data.get(i).getAuthor().getHeadUrl(),holder.cv);
        holder.tvName.setText(data.get(i).getAuthor().getNickName());
        holder.tvDate.setText(data.get(i).getUpdatedAt().substring(0,10));
        holder.tvMessage.setText(data.get(i).getContent());
        holder.tvZanNum.setText(data.get(i).getZan()+"");

        return view;
    }

    class CviewHolder{
        public CircleImageView cv;
        public TextView tvName;
        public TextView tvDate;
        public TextView tvMessage;
        public ImageView ivZan;
        public ImageView ivZi;
        public TextView tvZanNum;
        public TextView tvZiNum;

        public CviewHolder(View view) {
            cv = (CircleImageView) view.findViewById(R.id.cv_comment_icon);
            tvName = (TextView) view.findViewById(R.id.tv_comment_name);
            tvDate = (TextView) view.findViewById(R.id.tv_comment_time);
            tvMessage = (TextView) view.findViewById(R.id.tv_comment_message);
            tvZanNum = (TextView) view.findViewById(R.id.tv_comment_zannum);
            tvZiNum = (TextView) view.findViewById(R.id.tv_comment_zinum);
            ivZan = (ImageView) view.findViewById(R.id.iv_comment_zan);
            ivZi = (ImageView) view.findViewById(R.id.iv_comment_zicomment);
        }
    }
}