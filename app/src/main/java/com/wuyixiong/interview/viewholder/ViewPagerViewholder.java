package com.wuyixiong.interview.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.github.why168.LoopViewPagerLayout;
import com.github.why168.listener.OnBannerItemClickListener;
import com.github.why168.loader.OnDefaultImageViewLoader;
import com.github.why168.modle.BannerInfo;
import com.github.why168.modle.IndicatorLocation;
import com.github.why168.modle.LoopStyle;
import com.wuyixiong.interview.R;

import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2017-5-7.
 */

public class ViewPagerViewholder extends RecyclerView.ViewHolder implements OnBannerItemClickListener {
    private LoopViewPagerLayout mLoopViewPagerLayout;
    private ArrayList<View> list = new ArrayList<>();
    private Context context;

    public ViewPagerViewholder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        mLoopViewPagerLayout = (LoopViewPagerLayout)itemView.findViewById(R.id.mLoopViewPagerLayout);
        init();
    }

    /**
     * 初始化轮播图
     */
    public void init(){
        mLoopViewPagerLayout.setLoop_ms(3000);//轮播的速度(毫秒)
        mLoopViewPagerLayout.setLoop_duration(1000);//滑动的速率(毫秒)
        mLoopViewPagerLayout.setLoop_style(LoopStyle.Empty);//轮播的样式-默认empty
        mLoopViewPagerLayout.setIndicatorLocation(IndicatorLocation.Center);//指示器位置-中Center
        mLoopViewPagerLayout.setLoop_style(LoopStyle.Zoom);//设置轮播样式
//        mLoopViewPagerLayout.setNormalBackground(R.drawable.normal_background);//默认指示器颜色
//        mLoopViewPagerLayout.setSelectedBackground(R.drawable.selected_background);//选中指示器颜色
//        mLoopViewPagerLayout.setIndicatorLocation(IndicatorLocation.Center);//设置小圆点位置
        mLoopViewPagerLayout.initializeData(context);//初始化数据
        ArrayList<BannerInfo> bannerInfos = new ArrayList<>();
        bannerInfos.add(new BannerInfo<Integer>(R.drawable.ic_login_sina_weibo, "第一张图片"));
        bannerInfos.add(new BannerInfo<Integer>(R.drawable.ic_login_tencent_qq, "第二张图片"));
        bannerInfos.add(new BannerInfo<Integer>(R.drawable.ic_login_wechat, "第三张图片"));
        mLoopViewPagerLayout.setOnLoadImageViewListener(new OnDefaultImageViewLoader() {
            @Override
            public void onLoadImageView(ImageView imageView, Object parameter) {
                imageView.setBackgroundResource((int)parameter);
            }
        });//设置图片加载&自定义图片监听
        mLoopViewPagerLayout.setOnBannerItemClickListener(this);//设置监听
        mLoopViewPagerLayout.setLoopData(bannerInfos);//设置数据
        mLoopViewPagerLayout.startLoop();
    }
    public void stop(){
        mLoopViewPagerLayout.stopLoop();
    }


    @Override
    public void onBannerClick(int index, ArrayList<BannerInfo> banner) {

    }
}
