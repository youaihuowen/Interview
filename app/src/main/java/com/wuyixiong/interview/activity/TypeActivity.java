package com.wuyixiong.interview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.adapter.TypeAdapter;
import com.wuyixiong.interview.base.BaseActivity;
import com.wuyixiong.interview.entity.SelectType;

import java.util.ArrayList;

public class TypeActivity extends BaseActivity {

    private TextView done;
    private ListView listView;
    private TypeAdapter typeAdapter;
    private ArrayList<SelectType> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        initView();
        initData();
        listView.setAdapter(typeAdapter);
        typeAdapter.notifyDataSetChanged();
        setListener();
    }

    @Override
    protected void initView() {
        done = (TextView) findViewById(R.id.tv_done);
        listView = (ListView) findViewById(R.id.lv_type);
    }

    protected void initData(){
        SelectType type1 = new SelectType("Android",true);
        SelectType type2 = new SelectType("ios",false);
        SelectType type3 = new SelectType("Html",true);
        SelectType type4 = new SelectType("C++",false);
        SelectType type5 = new SelectType("Java",false);
        list.add(type1);
        list.add(type2);
        list.add(type3);
        list.add(type4);
        list.add(type5);
        typeAdapter = new TypeAdapter(this);
        typeAdapter.setData(list);
    }

    @Override
    protected void setListener() {
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
