package com.wuyixiong.interview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
    private TextView titleText;
    private ImageView titleMenu;

    private HomeFragment homeFragment;
    private InterviewFragment interviewFragment;
    private MessageFragment messageFragment;
    private MineFragment mineFragment;

    private FragmentManager manager;

    public static Boolean havaNetwork;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定控件
        initView();
        //创建主页的fragment
        homeFragment = new HomeFragment();
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.content, homeFragment);
        transaction.commit();
        iv_home.setSelected(true);
        //判断是否有网
        havaNetwork = isNetworkAvailable(this);
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
        titleMenu = (ImageView) findViewById(R.id.iv_title_menu);
        titleText = (TextView) findViewById(R.id.tv_title_text);


    }

    @Override
    protected void setListener() {
        ll_home.setOnClickListener(this);
        ll_interview.setOnClickListener(this);
        ll_message.setOnClickListener(this);
        ll_mine.setOnClickListener(this);
        titleMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_home_main:
                setTitle("首页", true);
                showFragment(0);
                selected(0);
                break;
            case R.id.ll_interview_main:
                setTitle("面试", false);
                showFragment(1);
                selected(1);
                break;
            case R.id.ll_message_main:
                setTitle("消息", false);
                showFragment(2);
                selected(2);
                break;
            case R.id.ll_mine_main:
                setTitle("我的", false);
                showFragment(3);
                selected(3);
                break;
            case R.id.iv_title_menu:
                startActivity(new Intent(this, TypeActivity.class));
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

    /**
     * 设置title
     *
     * @param text title上显示的文字
     * @param b    true时显示menu false 时不显示menu
     */
    public void setTitle(String text, Boolean b) {
        titleText.setText(text);
        if (b) {
            titleMenu.setVisibility(View.VISIBLE);
        } else {
            titleMenu.setVisibility(View.GONE);
        }

    }

    /**
     * 切换显示的fragment
     * @param id
     */
    public void showFragment(int id){
        FragmentTransaction t = manager.beginTransaction();
        hideAllFragment(t);
       switch (id){
           case 0:
               if (homeFragment != null) {
                   t.show(homeFragment);

               } else {
                   homeFragment = new HomeFragment();
                   t.add(R.id.content, homeFragment);
               }
               break;
           case 1:
               if (interviewFragment != null) {
                   t.show(interviewFragment);
               } else {
                   interviewFragment = new InterviewFragment();
                   t.add(R.id.content, interviewFragment);
               }
               break;
           case 2:
               if (messageFragment != null) {
                   t.show(messageFragment);
               } else {
                   messageFragment = new MessageFragment();
                   t.add(R.id.content, messageFragment);
               }
               break;
           case 3:
               if (mineFragment != null) {
                   t.show(mineFragment);
               } else {
                   mineFragment = new MineFragment();
                   t.add(R.id.content, mineFragment);
               }
               break;
       }
       t.commit();
    }

    /**
     * 隐藏所有fragment
     * @param t
     */
    public void hideAllFragment(FragmentTransaction t){
        if (homeFragment != null)
            t.hide(homeFragment);
        if (interviewFragment != null)
            t.hide(interviewFragment);
        if (messageFragment !=null)
            t.hide(messageFragment);
        if (mineFragment !=null)
            t.hide(mineFragment);
    }


}
