package com.wuyixiong.interview.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.activity.DetailActivity;
import com.wuyixiong.interview.activity.MainActivity;
import com.wuyixiong.interview.adapter.NewsAdapter;
import com.wuyixiong.interview.base.BaseActivity;
import com.wuyixiong.interview.entity.News;
import com.wuyixiong.interview.event.SendNews;
import com.wuyixiong.interview.utils.Query;
import com.wuyixiong.interview.view.MyDecoration;
import com.yalantis.phoenix.PullToRefreshView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recycle;
    private PullToRefreshView rf;
    private NewsAdapter adapter;
    private ArrayList<String> queryWhere = new ArrayList<String>();
    private ArrayList<News> queryResult = new ArrayList<News>();


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        setListener();
        return view;
    }


    /**
     * 初始化控件
     *
     * @param view
     */
    private void initView(View view) {
        rf = (PullToRefreshView) view.findViewById(R.id.rf_contain);

        recycle = (RecyclerView) view.findViewById(R.id.recycle_home);
        GridLayoutManager manager = new GridLayoutManager(getActivity(),1);
        recycle.setLayoutManager(manager);
        recycle.addItemDecoration(new MyDecoration(getContext(),MyDecoration.VERTICAL_LIST));
        //初始化数据
        initData();

        recycle.setAdapter(adapter);
    }

    /**
     * 初始化数据
     */
    public void initData() {
        adapter = new NewsAdapter(getActivity());
        queryWhere.add("Android");
        queryWhere.add("Html");

        if (MainActivity.havaNetwork){
            Query.getInstance().queryNews(queryWhere);
            ((BaseActivity)getActivity()).showLoadingDialog("加载中",true);
        }else {
            Query.getInstance().queryNewsWithouNetwork(queryWhere);
            Toast.makeText(getActivity(),"网络连接不可用",Toast.LENGTH_LONG).show();
        }

    }


    public void setListener(){
        rf.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rf.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rf.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onFinishQuery(ArrayList<News> newses) {
        //查询完成
        if (newses.get(0) instanceof News){
            queryResult = newses;
            adapter.setData(queryResult);
            adapter.notifyDataSetChanged();
            ((BaseActivity)getActivity()).cancelDialog();
        }

    }
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void goDetails(SendNews sendNews) {
        //跳转到详细信息页面
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("url",sendNews.getUrl());
        intent.putExtra("news",sendNews.getNews());
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
