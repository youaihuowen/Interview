package com.wuyixiong.interview.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wuyixiong.interview.R;
import com.wuyixiong.interview.activity.CollectionActivity;
import com.wuyixiong.interview.activity.LoginActivity;
import com.wuyixiong.interview.activity.MainActivity;
import com.wuyixiong.interview.activity.MyInfoActivity;
import com.wuyixiong.interview.adapter.MineAdapter;
import com.wuyixiong.interview.base.BaseActivity;
import com.wuyixiong.interview.entity.MineItem;
import com.wuyixiong.interview.entity.News;
import com.wuyixiong.interview.entity.User;
import com.wuyixiong.interview.event.LoginedEvent;
import com.wuyixiong.interview.view.CircleImageView;

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
    private CircleImageView cv;
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
        cv = (CircleImageView) view.findViewById(R.id.cv_icon);
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
                if (islogin) {
                    startActivityForResult(new Intent(getActivity(), MyInfoActivity.class),
                            BaseActivity.LOGOUT_CODE);
                } else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        if (islogin) {
                            Intent intent = new Intent(getContext(), CollectionActivity.class);
                            intent.putExtra("id",0);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case 1:
                        if (islogin) {
                            Intent intent1 = new Intent(getContext(), CollectionActivity.class);
                            intent1.putExtra("id",1);
                            startActivity(intent1);
                        } else {
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            startActivity(intent);
                        }

                        break;
                    case 2:
                        if (islogin) {
                            Intent intent2 = new Intent(getContext(), CollectionActivity.class);
                            intent2.putExtra("id",2);
                            startActivity(intent2);
                        } else {
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case 4:
                        if (islogin) {

                        } else {
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case 5:

                        break;
                    default:
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
            if (((String) BmobUser.getObjectByKey("headUrl")) == null){
                cv.setImageResource(R.drawable.ic_unlogin);
            }else {
                ImageLoader.getInstance().displayImage((String) BmobUser.getObjectByKey("headUrl"), cv);
            }
        } else {
            islogin = false;
            cv.setImageResource(R.drawable.ic_unlogin);
            tvUlogin.setVisibility(View.VISIBLE);
            tvUsername.setText("username");
            tvUsername.setVisibility(View.GONE);
            tvnickname.setText("nickname");
            tvnickname.setVisibility(View.GONE);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onFinishQuery(LoginedEvent event) {
        if (event.isLogined()) {
            isLogined(true);
        } else {
            isLogined(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
