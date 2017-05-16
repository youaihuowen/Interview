package com.wuyixiong.interview.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.adapter.MessageAdapter;
import com.wuyixiong.interview.base.BaseActivity;
import com.wuyixiong.interview.entity.Message;
import com.wuyixiong.interview.entity.News;
import com.wuyixiong.interview.event.SendError;
import com.wuyixiong.interview.utils.Query;
import com.wuyixiong.interview.view.MyDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {


    private RecyclerView recyclerView;
    private MessageAdapter adapter;

    ArrayList<Message> queryResult;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        EventBus.getDefault().register(this);
        initView(view);
        initData();
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_message);
        GridLayoutManager manager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new MyDecoration(getContext(),MyDecoration.VERTICAL_LIST));
    }

    private void initData() {
        adapter = new MessageAdapter(getContext());
        ((BaseActivity) getActivity()).showLoadingDialog("加载中", true);
        Query.getInstance().queryMessage();
    }


    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onFinishQuery(ArrayList<Message> message) {
        //查询完成
        queryResult = message;
        adapter.setData(queryResult);
        adapter.notifyDataSetChanged();
        ((BaseActivity) getActivity()).cancelDialog();
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onError(SendError error) {
        if (error.getErrorId() == 1){
            ((BaseActivity) getActivity()).cancelDialog();
            Toast.makeText(getContext(), error.getErrorText(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
