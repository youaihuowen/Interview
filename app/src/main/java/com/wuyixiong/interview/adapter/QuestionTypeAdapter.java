package com.wuyixiong.interview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuyixiong.interview.R;

import java.util.ArrayList;

import static android.R.attr.data;

/**
 * Created by WUYIXIONG on 2017-5-13.
 */

public class QuestionTypeAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> list= new ArrayList<>();
    private int selected = 0;

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public QuestionTypeAdapter(Context mContext, ArrayList<String> data) {
        this.mContext = mContext;
        this.list = data;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view= LayoutInflater.from(mContext).inflate(R.layout.item_hor, null);
            holder = new ViewHolder((TextView) view.findViewById(R.id.tv_question_type));
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv.setText(list.get(i));
        if (i == selected){
            holder.tv.setTextColor(Color.rgb(12,133,255));
        }else {
            holder.tv.setTextColor(Color.rgb(200,203,212));
        }
        return view;
    }

    private class ViewHolder{
        private TextView tv;

        public ViewHolder(TextView tv) {
            this.tv = tv;
        }
    }
}
