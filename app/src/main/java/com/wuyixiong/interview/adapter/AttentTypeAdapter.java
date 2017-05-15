package com.wuyixiong.interview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.wuyixiong.interview.R;
import com.wuyixiong.interview.base.BaseActivity;
import com.wuyixiong.interview.entity.SelectType;

import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2017-5-11.
 */

public class AttentTypeAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<SelectType> data = new ArrayList<>();

    public AttentTypeAdapter(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<SelectType> data) {
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
        TypeViewHolder holder = null;
        if (view == null) {
            view= LayoutInflater.from(mContext).inflate(R.layout.item_type, null);
            holder = new TypeViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (TypeViewHolder) view.getTag();
        }
        holder.tv.setText(data.get(i).getType());
        holder.sb.setChecked(data.get(i).isSelected());
        return view;
    }

    class TypeViewHolder {
        public TextView tv;
        public SwitchButton sb;

        public TypeViewHolder(View v) {
            this.tv = (TextView) v.findViewById(R.id.tv_type);
            this.sb = (SwitchButton) v.findViewById(R.id.sb_type);
        }
    }

}
