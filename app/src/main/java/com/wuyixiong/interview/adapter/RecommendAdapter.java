package com.wuyixiong.interview.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wuyixiong.interview.R;
import com.wuyixiong.interview.activity.RecommedDetailActivity;
import com.wuyixiong.interview.entity.Recommend;
import com.wuyixiong.interview.viewholder.RecommendViewHolder;

import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2017-5-19.
 */

public class RecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<Recommend> data = new ArrayList<Recommend>();

    public RecommendAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(ArrayList<Recommend> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_recommend,null);
        return new RecommendViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecommendViewHolder viewHolder = (RecommendViewHolder) holder;
        viewHolder.title.setText(data.get(position).getTitle());
        String time = data.get(position).getUpdatedAt().substring(0,10);
        viewHolder.likeOrDate.setText(data.get(position).getLikeNum()+"人喜欢^"+time);
        if (data.get(position).getPic_url() != null){
            ImageLoader.getInstance().displayImage(data.get(position).getPic_url(),viewHolder.iv);
        }else {
            viewHolder.iv.setVisibility(View.GONE);
        }
        viewHolder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RecommedDetailActivity.class);
                intent.putExtra("url",data.get(position).getUrl());
                intent.putExtra("position",position);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
