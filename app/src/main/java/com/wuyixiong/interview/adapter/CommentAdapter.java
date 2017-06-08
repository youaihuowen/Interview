package com.wuyixiong.interview.adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wuyixiong.interview.R;
import com.wuyixiong.interview.entity.Comment;
import com.wuyixiong.interview.utils.Publication;
import com.wuyixiong.interview.view.CircleImageView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by WUYIXIONG on 2017-5-22.
 */

public class CommentAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Comment> data = new ArrayList<>();
    private ArrayList<String> flags = new ArrayList<>();
    private ListView mListView;

    public CommentAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<Comment> data) {
        this.data = data;
    }

    public void addData(Comment c) {
        data.add(0, c);
    }

    public void setListView(ListView mListView) {
        this.mListView = mListView;
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
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        CviewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_comment, null);
            holder = new CviewHolder(view);
            view.setTag(holder);
        } else {
            holder = (CviewHolder) view.getTag();
        }
        ImageLoader.getInstance().displayImage(data.get(i).getAuthor().getHeadUrl(), holder.cv);
        holder.tvName.setText(data.get(i).getAuthor().getNickName());
        holder.tvDate.setText(data.get(i).getUpdatedAt().substring(5, 16));
        holder.tvMessage.setText(data.get(i).getContent());
        holder.tvZanNum.setText(data.get(i).getZan() + "");
        if (flags != null && flags.size() > 0) {
            for (String flag : flags) {
                if (flag.equals(data.get(i).getObjectId())) {
                    holder.ivZan.setSelected(true);
                } else {
                    holder.ivZan.setSelected(false);
                }
            }
        }
        holder.ivZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                if (!view1.isSelected()) {
                    updateItem(mListView, i);
                    flags.add(data.get(i).getObjectId());
                }
            }
        });
        return view;
    }


    public void updateItem(ListView mListView, int index) {
        int visiblePosition = mListView.getFirstVisiblePosition();
        if (index - visiblePosition >= 0) {
            View view = mListView.getChildAt(index - visiblePosition);
            CviewHolder holder = (CviewHolder) view.getTag();

            holder.ivZan.setSelected(true);
            data.get(index).setZan(data.get(index).getZan() + 1);
            holder.tvZanNum.setText(data.get(index).getZan() + "");
            Publication.getInstance().zan(data.get(index).getObjectId(), data.get(index).getZan());

        }
    }


    class CviewHolder {
        public CircleImageView cv;
        public TextView tvName;
        public TextView tvDate;
        public TextView tvMessage;
        public ImageView ivZan;
        public ImageView ivZi;
        public TextView tvZanNum;
        public TextView tvZiNum;

        public CviewHolder(View view) {
            cv = (CircleImageView) view.findViewById(R.id.cv_comment_icon);
            tvName = (TextView) view.findViewById(R.id.tv_comment_name);
            tvDate = (TextView) view.findViewById(R.id.tv_comment_time);
            tvMessage = (TextView) view.findViewById(R.id.tv_comment_message);
            tvZanNum = (TextView) view.findViewById(R.id.tv_comment_zannum);
            tvZiNum = (TextView) view.findViewById(R.id.tv_comment_zinum);
            ivZan = (ImageView) view.findViewById(R.id.iv_comment_zan);
            ivZi = (ImageView) view.findViewById(R.id.iv_comment_zicomment);
        }
    }

}
