package com.wuyixiong.interview.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.adapter.ResultAdapter;
import com.wuyixiong.interview.entity.Test;
import com.wuyixiong.interview.entity.User;
import com.wuyixiong.interview.event.ResultEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {


    @Bind(R.id.tv_persent)
    TextView tvPersent;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.rv_list)
    RecyclerView rv;
    private ArrayList<Test> list;
    private ResultAdapter adapter;

    private int persent;

    public ResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this, view);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 6);
        rv.setLayoutManager(manager);
        adapter = new ResultAdapter(getContext());
        rv.setAdapter(adapter);
        return view;
    }

    public void initData() {
        float err = 0, well = 0;
        for (Test test : list) {
            if (test.getRight() == test.getTemp()) {
                well++;
            } else {
                err++;
            }
        }
        float p = well / (list.size()) * 100;
        persent = (int) (p + 0.5f);


        User user = BmobUser.getCurrentUser(User.class);
        tvName.setText(user.getNickName());
        tvType.setText(list.get(0).getType());
        tvPersent.setText(persent+"%");
    }


    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void toResult(ResultEvent event) {
        if (event.isDone()) {
            list = event.getList();
            initData();
            adapter.setData(list);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
