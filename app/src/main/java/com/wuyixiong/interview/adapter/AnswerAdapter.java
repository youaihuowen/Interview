package com.wuyixiong.interview.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.entity.Question;

import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2017-5-18.
 */

public class AnswerAdapter extends PagerAdapter {

    private ArrayList<Question> list = new ArrayList<>();
    private Context mContext;

    public AnswerAdapter(Context context) {
        mContext = context;
    }

    public void setList(ArrayList<Question> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_answer,null);
        ((TextView)view.findViewById(R.id.tv_problem)).setText(list.get(position).getTitle());
        ((TextView)view.findViewById(R.id.tv_answer)).setText(list.get(position).getContent());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
