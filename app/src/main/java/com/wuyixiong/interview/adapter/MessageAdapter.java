package com.wuyixiong.interview.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wuyixiong.interview.R;
import com.wuyixiong.interview.activity.MessageDetailActivity;
import com.wuyixiong.interview.entity.Message;
import com.wuyixiong.interview.viewholder.MessageViewHolder0;
import com.wuyixiong.interview.viewholder.MessageViewHolder1;
import com.wuyixiong.interview.viewholder.MessageViewHolder3;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by WUYIXIONG on 2017-5-15.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Message> data = new ArrayList<>();

    public MessageAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    public void setData(ArrayList<Message> data) {
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getPicType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0){
            View view = inflater.inflate(R.layout.item_message_type0,null);
            return new MessageViewHolder0(view);
        }else if (viewType == 1 || viewType == 2){
            View view1 = inflater.inflate(R.layout.item_message_type1,null);
            return new MessageViewHolder1(view1);
        }else {
            View view2 = inflater.inflate(R.layout.item_message_type3,null);
            return new MessageViewHolder3(view2);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MessageViewHolder0){
            MessageViewHolder0 viewHolder0 = (MessageViewHolder0) holder;
            //共有的
            viewHolder0.authorName.setText(data.get(position).getAuthor().getNickName());
            viewHolder0.type.setText(data.get(position).getType());
            viewHolder0.company.setText(data.get(position).getCompany());
            viewHolder0.date.setText(data.get(position).getUpdatedAt());
            viewHolder0.message.setText(data.get(position).getContents());
            ImageLoader.getInstance().displayImage(data.get(position).getAuthor().getHeadUrl(),
                    viewHolder0.icon);
            viewHolder0.contents.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, MessageDetailActivity.class);
                    intent.putExtra("message",data.get(position));
                    mContext.startActivity(intent);
                }
            });

        }else if (holder instanceof MessageViewHolder1){
            MessageViewHolder1 viewHolder1 = (MessageViewHolder1) holder;
            //共有的
            viewHolder1.authorName.setText(data.get(position).getAuthor().getNickName());
            viewHolder1.type.setText(data.get(position).getType());
            viewHolder1.company.setText(data.get(position).getCompany());
            viewHolder1.date.setText(data.get(position).getUpdatedAt());
            viewHolder1.message.setText(data.get(position).getContents());
            ImageLoader.getInstance().displayImage(data.get(position).getAuthor().getHeadUrl(),
                    viewHolder1.icon);
            //type1特有的
            ImageLoader.getInstance().displayImage(data.get(position).getPic1(),viewHolder1.pic1);
            viewHolder1.contents.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, MessageDetailActivity.class);
                    intent.putExtra("message",data.get(position));
                    mContext.startActivity(intent);
                }
            });

        }else{
            MessageViewHolder3 viewHolder3 = (MessageViewHolder3) holder;
            //共有的
            viewHolder3.authorName.setText(data.get(position).getAuthor().getNickName());
            viewHolder3.type.setText(data.get(position).getType());
            viewHolder3.company.setText(data.get(position).getCompany());
            viewHolder3.date.setText(data.get(position).getUpdatedAt());
            viewHolder3.message.setText(data.get(position).getContents());
            ImageLoader.getInstance().displayImage(data.get(position).getAuthor().getHeadUrl(),
                    viewHolder3.icon);
            //type3特有的
            ImageLoader.getInstance().displayImage(data.get(position).getPic1(),viewHolder3.pic1);
            ImageLoader.getInstance().displayImage(data.get(position).getPic2(),viewHolder3.pic2);
            ImageLoader.getInstance().displayImage(data.get(position).getPic3(),viewHolder3.pic3);
            viewHolder3.contents.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, MessageDetailActivity.class);
                    intent.putExtra("message",data.get(position));
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
