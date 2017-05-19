package com.wuyixiong.interview.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.adapter.RecommendAdapter;
import com.wuyixiong.interview.base.BaseActivity;
import com.wuyixiong.interview.entity.News;
import com.wuyixiong.interview.entity.Recommend;
import com.wuyixiong.interview.event.RecommendEvent;
import com.wuyixiong.interview.utils.Query;
import com.wuyixiong.interview.view.MyDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class RecommendActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title_type)
    TextView tvTitleType;

    private RecyclerView recycle;
    private RecommendAdapter adapter;
    private String recommendType;
    private ArrayList<Recommend> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        ButterKnife.bind(this);
        recommendType = getIntent().getStringExtra("recommendtype");
        EventBus.getDefault().register(this);

        initView();


        GridLayoutManager manager = new GridLayoutManager(this,1);
        recycle.setLayoutManager(manager);
        recycle.addItemDecoration(new MyDecoration(this,MyDecoration.VERTICAL_LIST));

        adapter = new RecommendAdapter(this);
        recycle.setAdapter(adapter);
        Query.getInstance().queryRecommend(recommendType);

        setListener();
    }

    @Override
    protected void initView() {
        recycle = (RecyclerView) findViewById(R.id.rv_recommend);

        switch (recommendType){
            case "recommend":
                tvTitleType.setText("火爆推荐");
                break;
            case "company":
                tvTitleType.setText("官方面经");

                break;
            case "offline":
                tvTitleType.setText("线下活动");

                break;
            case "skill":
                tvTitleType.setText("面试技巧");

                break;
        }
    }

    @Override
    protected void setListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onFinishQuery(RecommendEvent event) {
        //查询完成
        list = event.getList();
        adapter.setData(list);
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
