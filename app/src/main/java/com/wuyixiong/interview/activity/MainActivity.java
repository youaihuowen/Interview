package com.wuyixiong.interview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuyixiong.interview.base.BaseActivity;
import com.wuyixiong.interview.R;
import com.wuyixiong.interview.fragment.HomeFragment;
import com.wuyixiong.interview.fragment.InterviewFragment;
import com.wuyixiong.interview.fragment.MessageFragment;
import com.wuyixiong.interview.fragment.MineFragment;

import cn.bmob.v3.Bmob;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_home, ll_interview, ll_message, ll_mine;
    private TextView tv_home, tv_interview, tv_message, tv_mine;
    private TextView[] tvs = new TextView[4];
    private ImageView iv_home, iv_interview, iv_message, iv_mine;
    private ImageView[] ivs = new ImageView[4];
    private FrameLayout content;

    private HomeFragment homeFragment;
    private InterviewFragment interviewFragment;
    private MessageFragment messageFragment;
    private MineFragment mineFragment;

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定控件
        initView();
        //创建主页的fragment
        homeFragment = new HomeFragment();
        manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.content, homeFragment).commit();
        iv_home.setSelected(true);
        //初始化Bmob
        Bmob.initialize(this, "ce69e04181855972b059106c651b6433");
        //设置监听
        setListener();
    }

    @Override
    protected void initView() {
        ll_home = (LinearLayout) findViewById(R.id.ll_home_main);
        ll_interview = (LinearLayout) findViewById(R.id.ll_interview_main);
        ll_message = (LinearLayout) findViewById(R.id.ll_message_main);
        ll_mine = (LinearLayout) findViewById(R.id.ll_mine_main);

        tv_home = (TextView) findViewById(R.id.tv_home_main);
        tv_interview = (TextView) findViewById(R.id.tv_interview_main);
        tv_message = (TextView) findViewById(R.id.tv_message_main);
        tv_mine = (TextView) findViewById(R.id.tv_mine_main);
        tvs[0] = tv_home;
        tvs[1] = tv_interview;
        tvs[2] = tv_message;
        tvs[3] = tv_mine;

        iv_home = (ImageView) findViewById(R.id.iv_home_main);
        iv_interview = (ImageView) findViewById(R.id.iv_interview_main);
        iv_message = (ImageView) findViewById(R.id.iv_message_main);
        iv_mine = (ImageView) findViewById(R.id.iv_mine_main);
        ivs[0] = iv_home;
        ivs[1] = iv_interview;
        ivs[2] = iv_message;
        ivs[3] = iv_mine;

        content = (FrameLayout) findViewById(R.id.content);


    }

    @Override
    protected void setListener() {
        ll_home.setOnClickListener(this);
        ll_interview.setOnClickListener(this);
        ll_message.setOnClickListener(this);
        ll_mine.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_home_main:
                if (homeFragment != null) {
                    manager.beginTransaction().replace(R.id.content, homeFragment).commit();
                } else {
                    homeFragment = new HomeFragment();
                    manager.beginTransaction().replace(R.id.content, homeFragment).commit();
                }
                selected(0);
                break;
            case R.id.ll_interview_main:
                if (interviewFragment != null) {
                    manager.beginTransaction().replace(R.id.content, interviewFragment).commit();
                } else {
                    interviewFragment = new InterviewFragment();
                    manager.beginTransaction().replace(R.id.content, interviewFragment).commit();
                }
                selected(1);
                break;
            case R.id.ll_message_main:
                if (messageFragment != null) {
                    manager.beginTransaction().replace(R.id.content, messageFragment).commit();
                } else {
                    messageFragment = new MessageFragment();
                    manager.beginTransaction().replace(R.id.content, messageFragment).commit();
                }
                selected(2);
                break;
            case R.id.ll_mine_main:
                if (mineFragment != null) {
                    manager.beginTransaction().replace(R.id.content, mineFragment).commit();
                } else {
                    mineFragment = new MineFragment();
                    manager.beginTransaction().replace(R.id.content, mineFragment).commit();
                }
                selected(3);
                break;
        }
    }

    /**
     * 根据fragment的选择改变图像
     *
     * @param a 选择的序号
     */
    public void selected(int a) {
        for (int i = 0; i < 4; i++) {
            if (i == a) {
                tvs[i].setTextColor(getResources().getColor(R.color.selected));
                ivs[i].setSelected(true);
            } else {
                tvs[i].setTextColor(getResources().getColor(R.color.unselected));
                ivs[i].setSelected(false);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        onClick(ll_home);
    }
}
