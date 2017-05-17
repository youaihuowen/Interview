package com.wuyixiong.interview.activity;

import android.os.Bundle;
import android.widget.Button;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.base.BaseActivity;
import com.wuyixiong.interview.entity.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class MyInfoActivity extends BaseActivity {


    @Bind(R.id.btn_quit)
    Button btnQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.btn_quit)
    public void onViewClicked() {
        BmobUser.logOut();
        finish();
    }
}
