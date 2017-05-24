package com.wuyixiong.interview.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.adapter.CollectionAdapter;
import com.wuyixiong.interview.base.BaseActivity;
import com.wuyixiong.interview.entity.News;
import com.wuyixiong.interview.event.SendMessageList;
import com.wuyixiong.interview.event.SendNewsList;
import com.wuyixiong.interview.event.SendQuestionList;
import com.wuyixiong.interview.utils.CollectionOperate;
import com.wuyixiong.interview.view.MyDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CollectionActivity extends BaseActivity {

    @Bind(R.id.tv_title_text)
    TextView tvTitle;
    @Bind(R.id.recycle_collection)
    RecyclerView recycle;
    private int id;
    private CollectionAdapter adapter;
    private SharedPreferences shared;
    private String newsCollectionId;
    private String questionCollectionId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra("id", -1);

        initView();

        adapter = new CollectionAdapter(this, id);
        recycle.setAdapter(adapter);

        setData(id);

    }

    @Override
    protected void initView() {
        recycle = (RecyclerView) findViewById(R.id.recycle_collection);
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        recycle.setLayoutManager(manager);
        recycle.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
    }

    @Override
    protected void setListener() {

    }

    public void setData(int id) {
        shared = getSharedPreferences("collectionId", MODE_PRIVATE);
        newsCollectionId = shared.getString("newsCollectionId", "null");
        questionCollectionId = shared.getString("questionCollectionId", null);
        if (id == 0) {
            CollectionOperate.getInstance().getAllNewsCollection(newsCollectionId);
        } else if (id == 1) {
            CollectionOperate.getInstance().getAllQuestionCollection(questionCollectionId);
        } else if (id == 2) {
            CollectionOperate.getInstance().queryOnesMessage();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onFinishNewsQuery(SendNewsList event) {
        //查询完成
        adapter.setList(event.getList());
        adapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onFinishQuestionQuery(SendQuestionList event) {
        //查询完成
        adapter.setList(event.getList());
        adapter.notifyDataSetChanged();
    }
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onFinishMessageQuery(SendMessageList event) {
        //查询完成
        adapter.setList(event.getList());
        adapter.notifyDataSetChanged();
    }
}
