package com.wuyixiong.interview.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.entity.Test;

import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2017-6-8.
 */

public class TestAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<Test> data = new ArrayList<>();

    private int id1, id2, id3, id4;

    public TestAdapter(Context mContext) {
        this.mContext = mContext;
        id1 = R.id.radio1;
        id2 = R.id.radio2;
        id3 = R.id.radio3;
        id4 = R.id.radio4;
    }

    public void setData(ArrayList<Test> data) {
        this.data = data;
    }

    public ArrayList<Test> getData() {
        return data;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager_test, null);
        ((TextView) view.findViewById(R.id.tv_title)).setText(data.get(position).getTitle());
        ((RadioButton) view.findViewById(R.id.radio1)).setText("A " + data.get(position).getAnswer1());
        ((RadioButton) view.findViewById(R.id.radio2)).setText("B " + data.get(position).getAnswer2());
        ((RadioButton) view.findViewById(R.id.radio3)).setText("C " + data.get(position).getAnswer3());
        ((RadioButton) view.findViewById(R.id.radio4)).setText("D " + data.get(position).getAnswer4());
        switch (data.get(position).getTemp()) {
            case 1:
                ((RadioGroup) view.findViewById(R.id.group)).check(id1);
                break;
            case 2:
                ((RadioGroup) view.findViewById(R.id.group)).check(id2);
                break;
            case 3:
                ((RadioGroup) view.findViewById(R.id.group)).check(id3);
                break;
            case 4:
                ((RadioGroup) view.findViewById(R.id.group)).check(id4);
                break;
            default:
        }
        ((RadioGroup) view.findViewById(R.id.group))
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        if (i == id1) {
                            data.get(position).setTemp(1);
                        } else if (i == id2) {
                            data.get(position).setTemp(2);
                        } else if (i == id3) {
                            data.get(position).setTemp(3);
                        } else if (i == id4) {
                            data.get(position).setTemp(4);
                        }

                        Log.i("tag", "----------------- i: " + i);
                        Log.i("tag", "----------------- temp: " + data.get(position).getTemp());
                    }
                });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
