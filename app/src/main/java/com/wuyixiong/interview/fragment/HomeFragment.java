package com.wuyixiong.interview.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.wuyixiong.interview.event.SendUrl;
import com.wuyixiong.interview.utils.Query;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import cn.bmob.v3.BmobQuery;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recycle;
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
        return view;
    }

    /**
     * 初始化控件
     *
     * @param view
     */
    private void initView(View view) {

        recycle = (RecyclerView) view.findViewById(R.id.recycle_home);
//        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager manager = new GridLayoutManager(getActivity(),1);
        recycle.setLayoutManager(manager);
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
        }else if ((new BmobQuery<News>()).hasCachedResult(News.class)){
            Query.getInstance().queryNewsWithouNetwork(queryWhere);
            Toast.makeText(getActivity(),"网络连接不可用",Toast.LENGTH_LONG).show();
            ((BaseActivity)getActivity()).showLoadingDialog("加载中",true);
        }else {
            Toast.makeText(getActivity(),"网络连接不可用",Toast.LENGTH_LONG).show();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onFinishQuery(ArrayList<News> newses) {
        //查询完成
        queryResult = newses;
        adapter.setData(queryResult);
        adapter.notifyDataSetChanged();
        ((BaseActivity)getActivity()).cancelDialog();
    }
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void goDetails(SendUrl sendUrl) {
        //跳转到详细信息页面
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("url",sendUrl.getUrl());
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
