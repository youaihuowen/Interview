package com.wuyixiong.interview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.entity.Test;
import com.wuyixiong.interview.event.UnResultEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2017-6-8.
 */

public class ResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<Test> data = new ArrayList<>();

    public ResultAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(ArrayList<Test> data) {
        this.data = data;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_result,null);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ResultViewHolder viewHolder = (ResultViewHolder) holder;
        viewHolder.tv.setText("第"+(position+1)+"题");
        if (data.get(position).getTemp() != data.get(position).getRight()){
            viewHolder.tv.setBackgroundResource(R.drawable.shap_text_err);
        }else {
            viewHolder.tv.setBackgroundResource(R.drawable.shap_text);
        }
        viewHolder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new UnResultEvent(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ResultViewHolder extends RecyclerView.ViewHolder{
        public TextView tv;
        public ResultViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_item_result);
        }
    }
}

