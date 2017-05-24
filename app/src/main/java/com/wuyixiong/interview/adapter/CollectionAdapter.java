package com.wuyixiong.interview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wuyixiong.interview.R;
import com.wuyixiong.interview.entity.Message;
import com.wuyixiong.interview.entity.News;
import com.wuyixiong.interview.entity.Question;
import com.wuyixiong.interview.event.SendNews;
import com.wuyixiong.interview.viewholder.MsgCollectionViewHolder;
import com.wuyixiong.interview.viewholder.NewsViewHolder;
import com.wuyixiong.interview.viewholder.QuestionViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by WUYIXIONG on 2017-5-20.
 */

public class CollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private int type;
    private ArrayList list = new ArrayList();

    public CollectionAdapter(Context mContext, int type) {
        this.mContext = mContext;
        this.type = type;
    }

    public void setList(ArrayList list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (type == 0) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_news_home, null);
            return new NewsViewHolder(view);
        } else if (type == 1) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_question, null);
            return new QuestionViewHolder(view);
        } else if (type == 2) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_message_collection,null);
            return new MsgCollectionViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NewsViewHolder) {
            NewsViewHolder viewHolder = (NewsViewHolder) holder;
            viewHolder.newsTitle.setText(((News) list.get(position)).getTitle());
            viewHolder.newsTime.setText(((News) list.get(position)).getUpdatedAt().substring(0, 10));
            ImageLoader.getInstance().displayImage(((News) list.get(position)).getPic_url(),
                    viewHolder.newsImage);
            viewHolder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SendNews sendNews = new SendNews();
                    sendNews.setUrl(((News) list.get(position)).getUrl());
                    sendNews.setNews(((News) list.get(position)));
                    EventBus.getDefault().post(sendNews);

                }
            });

        } else if (holder instanceof QuestionViewHolder) {
            QuestionViewHolder viewHolder = (QuestionViewHolder) holder;
            viewHolder.tv.setText(((Question) list.get(position)).getTitle());
        } else if (holder instanceof MsgCollectionViewHolder){
            MsgCollectionViewHolder viewHolder = (MsgCollectionViewHolder) holder;
            viewHolder.tvName.setText(((Message)list.get(position)).getContents());
            viewHolder.tvTime.setText(((Message)list.get(position)).getUpdatedAt().substring(5,16));
            if (((Message)list.get(position)).getPicType() != 0){
                ImageLoader.getInstance().displayImage(((Message)list.get(position)).getPic1(),
                        viewHolder.ivPic);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
