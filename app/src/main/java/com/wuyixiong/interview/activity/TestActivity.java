package com.wuyixiong.interview.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.base.BaseActivity;
import com.wuyixiong.interview.event.ChangeEvent;
import com.wuyixiong.interview.event.QueryTypeEvent;
import com.wuyixiong.interview.event.ResultEvent;
import com.wuyixiong.interview.event.UnResultEvent;
import com.wuyixiong.interview.fragment.ResultFragment;
import com.wuyixiong.interview.fragment.TestFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TestActivity extends BaseActivity {


    @Bind(R.id.fl_test)
    FrameLayout flTest;

    private TestFragment testFragment;
    private ResultFragment resultFragment;
    private FragmentManager manager;
    public String type = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        type = getIntent().getStringExtra("type");
        initView();
    }

    @Override
    protected void initView() {
        testFragment = new TestFragment();
        resultFragment = new ResultFragment();
        manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.fl_test, testFragment);
        ft.add(R.id.fl_test, resultFragment);
        ft.hide(resultFragment);
        ft.commit();
    }

    @Override
    protected void setListener() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void toResult(ResultEvent event) {
        if (event.isDone()) {
            FragmentTransaction ft = manager.beginTransaction();
            ft.hide(testFragment);
            ft.show(resultFragment);
            ft.commit();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void toResult1(ChangeEvent event) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.hide(testFragment);
        ft.show(resultFragment);
        ft.commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void toTest(UnResultEvent event) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.hide(resultFragment);
        ft.show(testFragment);
        ft.commit();
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
