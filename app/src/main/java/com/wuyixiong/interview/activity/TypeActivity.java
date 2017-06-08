package com.wuyixiong.interview.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.adapter.AttentTypeAdapter;
import com.wuyixiong.interview.base.BaseActivity;
import com.wuyixiong.interview.entity.SelectType;

import java.util.ArrayList;

public class TypeActivity extends BaseActivity {

    private TextView done;
    private ListView listView;
    private AttentTypeAdapter typeAdapter;
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
        SharedPreferences shared =getSharedPreferences("interest",MODE_PRIVATE);
//        SharedPreferences.Editor editor = shared.edit();

        SelectType type1 = new SelectType("Android",shared.getBoolean("Android",false));
        SelectType type2 = new SelectType("ios",shared.getBoolean("ios",false));
        SelectType type3 = new SelectType("Html",shared.getBoolean("Html",false));
        SelectType type4 = new SelectType("C++",shared.getBoolean("C++",false));
        SelectType type5 = new SelectType("PHP",shared.getBoolean("PHP",false));
        SelectType type6 = new SelectType("SQL",shared.getBoolean("SQL",false));
        SelectType type7 = new SelectType("Linux",shared.getBoolean("Linux",false));
        SelectType type8 = new SelectType("Python",shared.getBoolean("Python",false));
        list.add(type1);
        list.add(type2);
        list.add(type3);
        list.add(type4);
        list.add(type5);
        list.add(type6);
        list.add(type7);
        list.add(type8);
        typeAdapter = new AttentTypeAdapter(this);
        typeAdapter.setData(list);
    }

    @Override
    protected void setListener() {
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeInterest(typeAdapter.getData());
                finish();
            }
        });
    }

    /**
     * 修改本地和后台保存的关注类型
     * @param list1
     */
    public void changeInterest(ArrayList<SelectType> list1){
        SharedPreferences shared =getSharedPreferences("interest",MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        for (SelectType type:list1) {
            editor.putBoolean(type.getType(),type.isSelected());
        }
        editor.commit();
    }
}
