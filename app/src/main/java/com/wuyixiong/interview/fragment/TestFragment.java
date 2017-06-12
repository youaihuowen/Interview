package com.wuyixiong.interview.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.adapter.TestAdapter;
import com.wuyixiong.interview.entity.Test;
import com.wuyixiong.interview.event.ChangeEvent;
import com.wuyixiong.interview.event.QueryTestEvent;
import com.wuyixiong.interview.event.ResultEvent;
import com.wuyixiong.interview.event.UnResultEvent;
import com.wuyixiong.interview.utils.Query;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {


    @Bind(R.id.vp_test)
    ViewPager vp;
    @Bind(R.id.btn_back)
    Button btnBack;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.btn_up)
    Button btnUp;
    @Bind(R.id.tv_rightAnswer)
    TextView tvRightAnswer;

    private String type;
    private ArrayList<Test> list;
    private TestAdapter adapter;
    private int size;


    public TestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        adapter = new TestAdapter(getContext());
        vp.setAdapter(adapter);
        initData();
        setListener();
        return view;
    }

    public void initData() {
        type = getActivity().getIntent().getStringExtra("type");
        Query.getInstance().queryTest(type);
    }

    public void setListener() {
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvNum.setText((position + 1) + "/" + size);
                switch (list.get(position).getRight()){
                    case 1:
                        tvRightAnswer.setText("正确答案：A");
                        break;
                    case 2:
                        tvRightAnswer.setText("正确答案：B");
                        break;
                    case 3:
                        tvRightAnswer.setText("正确答案：C");
                        break;
                    case 4:
                        tvRightAnswer.setText("正确答案：D");
                        break;
                    default:
                }

                if (position + 1 == size && !btnUp.getText().toString().equals("结果")) {
                    btnUp.setText("提交");
                } else if(!btnUp.getText().toString().equals("结果")) {
                    btnUp.setText("下一题");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.btn_back, R.id.tv_num, R.id.btn_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                if (!"退出".equals(btnBack.getText().toString())){
                    new AlertDialog.Builder(getContext()).setTitle("提示")
                            .setMessage("测验还没完成，是否退出")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    getActivity().finish();
                                }
                            })
                            .setNegativeButton("否",null)
                            .show();
                }else {
                    getActivity().finish();
                }
                break;
            case R.id.tv_num:
                break;
            case R.id.btn_up:
                if ("提交".equals(btnUp.getText().toString())) {
                    ArrayList<Test> list = adapter.getData();
                    EventBus.getDefault().post(new ResultEvent(list, true));
                } else if ("下一题".equals(btnUp.getText().toString())) {
                    int index = vp.getCurrentItem();
                    vp.setCurrentItem(index + 1);
                } else if ("结果".equals(btnUp.getText().toString())) {
                    EventBus.getDefault().post(new ChangeEvent());
                }

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onFinishQuery(QueryTestEvent event) {
        //查询完成
        if (event.isDone()) {
            list = event.getList();
            adapter.setData(event.getList());
            size = event.getList().size();
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void toTest(UnResultEvent event) {
        vp.setCurrentItem(event.getIndex());
        btnUp.setText("结果");
        btnBack.setText("退出");
        tvRightAnswer.setVisibility(View.VISIBLE);
    }
}
