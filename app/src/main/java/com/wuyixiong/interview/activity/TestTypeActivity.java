package com.wuyixiong.interview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.adapter.TestTypeAdapter;
import com.wuyixiong.interview.base.BaseActivity;
import com.wuyixiong.interview.event.QueryTypeEvent;
import com.wuyixiong.interview.utils.Query;
import com.wuyixiong.interview.view.MyDecoration;
import com.wuyixiong.interview.viewholder.TestTypeViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class TestTypeActivity extends BaseActivity {

    private RecyclerView recycle;
    private TestTypeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_type);
        initView();
        initDate();
    }

    @Override
    protected void initView() {
        recycle = (RecyclerView)findViewById(R.id.rv_testType);
        GridLayoutManager manager = new GridLayoutManager(this,2);
        recycle.setLayoutManager(manager);
//        recycle.addItemDecoration(new MyDecoration(this,MyDecoration.VERTICAL_LIST));
        adapter = new TestTypeAdapter(this);
        recycle.setAdapter(adapter);
    }

    @Override
    protected void setListener() {

    }
    protected void initDate(){
        showLoadingDialog("数据获取中",true);
        Query.getInstance().queryType();
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onFinishQuery(QueryTypeEvent event) {
        //查询完成
        cancelDialog();
        if (event.isDone()){
            adapter.setData(event.getList());
            adapter.notifyDataSetChanged();
        }else {
            toast("获取失败");
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
}
