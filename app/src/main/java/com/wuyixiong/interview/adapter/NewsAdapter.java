package com.wuyixiong.interview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.viewholder.NewsViewHolder;
import com.wuyixiong.interview.viewholder.ViewPagerViewholder;

/**
 * Created by WUYIXIONG on 2017-5-7.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    public NewsAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return 1;
        }else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1){
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager,null);
            return new ViewPagerViewholder(v,parent.getContext());
        }else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_news_home,null);
            return new NewsViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
