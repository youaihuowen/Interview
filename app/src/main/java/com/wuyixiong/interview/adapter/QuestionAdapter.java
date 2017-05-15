package com.wuyixiong.interview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.entity.Question;

import java.util.ArrayList;

import static android.R.id.list;
import static com.wuyixiong.interview.R.color.selected;

/**
 * Created by WUYIXIONG on 2017-5-13.
 */

public class QuestionAdapter extends BaseAdapter{
    private ArrayList<Question> data = new ArrayList<>();
    private Context mContext;

    public QuestionAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(ArrayList<Question> data) {
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
        Viewholder holder = null;
        if (view == null) {
            view= LayoutInflater.from(mContext).inflate(R.layout.item_question, null);
            holder = new Viewholder((TextView) view.findViewById(R.id.tv_question));
            view.setTag(holder);
        }else {
            holder = (Viewholder) view.getTag();
        }
        holder.tv.setText(data.get(i).getTitle());
        return view;
    }

    private class Viewholder{
        private TextView tv;

        public Viewholder(TextView tv) {
            this.tv = tv;
        }
    }
}
