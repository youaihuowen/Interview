package com.wuyixiong.interview.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.activity.LoginActivity;
import com.wuyixiong.interview.activity.MainActivity;
import com.wuyixiong.interview.activity.MyInfoActivity;
import com.wuyixiong.interview.adapter.MineAdapter;
import com.wuyixiong.interview.base.BaseActivity;
import com.wuyixiong.interview.entity.MineItem;
import com.wuyixiong.interview.entity.News;
import com.wuyixiong.interview.entity.User;
import com.wuyixiong.interview.event.LoginedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import cn.bmob.v3.BmobUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {

    private LinearLayout ll;
    private ListView list;
    private ArrayList<MineItem> data = new ArrayList<>();
    private MineAdapter adapter;

    private TextView tvUlogin;
    private TextView tvUsername;
    private TextView tvnickname;
    private boolean islogin = false;

    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView(view);
        EventBus.getDefault().register(this);
        initData();
        adapter = new MineAdapter(getContext());
        adapter.setData(data);
        list.setAdapter(adapter);

        //检查是否已经登录
        User myUser = BmobUser.getCurrentUser(User.class);
        if (myUser != null) {
//            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("check",0);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putBoolean("idLogin",true);
            isLogined(true);
        } else {
            isLogined(false);
        }
        setListener();
        return view;
    }


    /**
     * 初始化控件
     *
     * @param view
     */
    private void initView(View view) {
        ll = (LinearLayout) view.findViewById(R.id.ll_mine);
        list = (ListView) view.findViewById(R.id.lv_mine);
        tvnickname = (TextView) view.findViewById(R.id.tv_nick_mime);
        tvUsername = (TextView) view.findViewById(R.id.tv_user_mine);
        tvUlogin = (TextView) view.findViewById(R.id.tv_unlogin_mine);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (data.size() == 0) {
            MineItem a = new MineItem(R.drawable.ic_favorite, "我喜欢的", R.drawable.arrow_right);
            data.add(a);
            MineItem b = new MineItem(R.drawable.ic_collection, "我的题集", R.drawable.arrow_right);
            data.add(b);
            MineItem c = new MineItem(R.drawable.ic_message, "消息会话", R.drawable.arrow_right);
            data.add(c);

            MineItem d = new MineItem(0, " ", 0);
            d.setType(0);
            data.add(d);

            MineItem e = new MineItem(R.drawable.ic_feedback, "意见反馈", R.drawable.arrow_right);
            data.add(e);
            MineItem f = new MineItem(R.drawable.ic_settings, "设置", R.drawable.arrow_right);
            data.add(f);
        }

    }

    private void setListener() {
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (islogin){
                    startActivityForResult(new Intent(getActivity(),MyInfoActivity.class),
                            BaseActivity.LOGOUT_CODE);
                }else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 是否登录的不同操作
     *
     * @param b
     */
    public void isLogined(boolean b) {
        if (b) {
            islogin = true;

            String username = (String) BmobUser.getObjectByKey("username");
            String nickname = (String) BmobUser.getObjectByKey("nickName");
            tvUlogin.setVisibility(View.GONE);
            tvUsername.setText(username);
            tvUsername.setVisibility(View.VISIBLE);
            tvnickname.setText(nickname);
            tvnickname.setVisibility(View.VISIBLE);
        } else {
            islogin = false;

            tvUlogin.setVisibility(View.VISIBLE);
            tvUsername.setText("username");
            tvUsername.setVisibility(View.GONE);
            tvnickname.setText("nickname");
            tvnickname.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onFinishQuery(LoginedEvent event) {
        if (event.isLogined()){
            isLogined(true);
        }else {
            isLogined(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
