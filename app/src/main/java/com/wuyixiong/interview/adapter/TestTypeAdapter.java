package com.wuyixiong.interview.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wuyixiong.interview.R;
import com.wuyixiong.interview.activity.TestActivity;
import com.wuyixiong.interview.entity.Type;
import com.wuyixiong.interview.viewholder.TestTypeViewHolder;

import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2017-6-8.
 */

public class TestTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Type> data = new ArrayList<>();

    public TestTypeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(ArrayList<Type> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_test_type,null);
        return new TestTypeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TestTypeViewHolder viewHolder = (TestTypeViewHolder) holder;
        ImageLoader.getInstance().displayImage(data.get(position).getPic(),viewHolder.iv);
        viewHolder.tv.setText(data.get(position).getTypeName());
        viewHolder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TestActivity.class);
                intent.putExtra("type",data.get(position).getTypeName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

