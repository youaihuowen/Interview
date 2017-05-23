package com.wuyixiong.interview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.wuyixiong.interview.R;
import com.wuyixiong.interview.event.ShareEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2017-5-23.
 */

public class GridAdapter extends BaseAdapter {

    private ArrayList<String> listUrls = new ArrayList<>();
    private Context mContext;

    public GridAdapter(Context context) {
        mContext = context;
    }

    public void setListUrls(ArrayList<String> listUrls) {
        this.listUrls = listUrls;
    }

    @Override
    public int getCount() {
        return listUrls.size() + 1;
    }

    @Override
    public String getItem(int position) {
        if (position == listUrls.size()) {
            return null;
        } else
            return listUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_grade_addpic, null);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        if (position != listUrls.size()) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(mContext)
                    .load(new File(getItem(position)))
                    .placeholder(R.mipmap.default_error)
                    .error(R.mipmap.default_error)
                    .centerCrop()
                    .crossFade()
                    .into(imageView);

        }else {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new ShareEvent(true));
                }
            });
        }
        return convertView;
    }
}
