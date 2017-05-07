package com.wuyixiong.interview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.entity.MineItem;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by WUYIXIONG on 2017-4-28.
 */

public class MineAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<MineItem> list = new ArrayList<MineItem>();

    public MineAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }
    public void setData(ArrayList<MineItem> data){
        list=data;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        if (list.get(position).getType()== 0){
            convertView = inflater.inflate(R.layout.item_mine_null,null);
        }else {
            convertView = inflater.inflate(R.layout.item_mine,null);
            ((ImageView) convertView.findViewById(R.id.iv_item_mine)).
                    setImageResource(list.get(position).getImageID());
            ((TextView) convertView.findViewById(R.id.tv_item_mine))
                    .setText(list.get(position).getText());
            ((ImageView) convertView.findViewById(R.id.iv2_item_mine)).
                    setImageResource(list.get(position).getArrowID());
        }

//        ViewHolder holder = null;
//        if (convertView == null){
//            convertView = inflater.inflate(R.layout.item_mine,null);
//            holder = new ViewHolder((ImageView) convertView.findViewById(R.id.iv_item_mine),
//                    (TextView) convertView.findViewById(R.id.tv_item_mine),
//                    (ImageView) convertView.findViewById(R.id.iv2_item_mine));
//            convertView.setTag(holder);
//        }else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//            holder.imageView.setImageResource(list.get(position).getImageID());
//            holder.textView.setText(list.get(position).getText());
//            holder.arrow.setImageResource(list.get(position).getArrowID());
        return convertView;
    }


//    private class ViewHolder{
//        ImageView imageView;
//        TextView textView;
//        ImageView arrow;
//
//        public ViewHolder(ImageView imageView, TextView textView, ImageView arrow) {
//            this.imageView = imageView;
//            this.textView = textView;
//            this.arrow = arrow;
//        }
//    }
}
