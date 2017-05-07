package com.wuyixiong.interview.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.adapter.AutoScrollPagerAdapter;
import com.wuyixiong.interview.view.AutoScrollViewPager;

import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2017-5-7.
 */

public class ViewPagerViewholder extends RecyclerView.ViewHolder {
    private AutoScrollViewPager viewPager;
    private ArrayList<View> list = new ArrayList<>();
    private Context context;

    public ViewPagerViewholder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        viewPager = (AutoScrollViewPager) itemView.findViewById(R.id.autoVp);
        init();
    }

    public AutoScrollViewPager getViewPager() {
        return viewPager;
    }

    /**
     * 初始化轮播图
     */
    public void init(){
        ImageView iv = new ImageView(context);
        iv.setBackgroundResource(R.drawable.ic_login_sina_weibo);

        ImageView iv1 = new ImageView(context);
        iv1.setBackgroundResource(R.drawable.ic_login_tencent_qq);

        ImageView iv2 = new ImageView(context);
        iv2.setBackgroundResource(R.drawable.ic_login_wechat);

        list.add(iv);
        list.add(iv1);
        list.add(iv2);

        AutoScrollPagerAdapter adapter = new AutoScrollPagerAdapter(list);
        viewPager.setAdapter(adapter);
        viewPager.startAutoScroll();
        viewPager.setInterval(3000);
    }
}
