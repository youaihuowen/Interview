package com.wuyixiong.interview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wuyixiong.interview.R;
import com.wuyixiong.interview.entity.News;
import com.wuyixiong.interview.event.SendNews;
import com.wuyixiong.interview.viewholder.NewsViewHolder;
import com.wuyixiong.interview.viewholder.ViewPagerViewholder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2017-5-7.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<News> data = new ArrayList<News>();

    public NewsAdapter(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<News> data) {
        this.data = data;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NewsViewHolder){
            NewsViewHolder newsViewHolder = (NewsViewHolder)holder;
            newsViewHolder.newsTitle.setText(data.get(position-1).getTitle());
            newsViewHolder.newsTime.setText(data.get(position-1).getUpdatedAt().substring(0,10));
            ImageLoader.getInstance().displayImage(data.get(position-1).getPic_url(),
                    newsViewHolder.newsImage);
            newsViewHolder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SendNews sendNews = new SendNews();
                    sendNews.setUrl(data.get(position-1).getUrl());
                    sendNews.setNews(data.get(position-1));
                    EventBus.getDefault().post(sendNews);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size()+1;
    }
}
