package com.wuyixiong.interview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.base.BaseActivity;
import com.wuyixiong.interview.entity.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.edit_name)
    EditText editName;
    @Bind(R.id.edit_pwd)
    EditText editPwd;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.tv_findpwd)
    TextView tvFindpwd;
    @Bind(R.id.tv_register)
    TextView tvRegister;
    @Bind(R.id.iv_login_qq)
    ImageView ivLoginQq;
    @Bind(R.id.iv_login_wechat)
    ImageView ivLoginWechat;
    @Bind(R.id.iv_login_weibo)
    ImageView ivLoginWeibo;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        //设置toolbar的返回键
        toolbar.setNavigationIcon(R.drawable.toolbar_back_white);
        setListener();

    }

    @Override
    protected void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    /**
     * toolbar 返回键的监听
     */
    @Override
    protected void setListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @OnClick({R.id.btn_login, R.id.tv_findpwd, R.id.tv_register, R.id.iv_login_qq, R.id.iv_login_wechat, R.id.iv_login_weibo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String username = editName.getText().toString();
                String password = editPwd.getText().toString();
                if (username.length()>0 && password.length()>0){
                    showLoadingDialog("登录中",true);
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.login(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (e == null){
                                cancelDialog();
                                goMainActivity();
                            }else {
                                cancelDialog();
                                Log.i("tag", "------------- "+e);
                                if (e.getErrorCode() == 9016){
                                    toast("网络不可用，请检查网络连接");
                                }else {
                                    toast("账号密码不正确");
                                }
                                editPwd.setText("");
                                editName.setText("");
                            }
                        }
                    });
                }else {
                    toast("账号密码不能为空");
                }
                break;
            case R.id.tv_findpwd:
                break;
            case R.id.tv_register:
                startActivityForResult(new Intent(this,RegisterActivity.class), FINISH_TOGETHER);
                break;
            case R.id.iv_login_qq:
                break;
            case R.id.iv_login_wechat:
                break;
            case R.id.iv_login_weibo:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == FINISH_TOGETHER){
            finish();
        }
    }

    /**
     * 跳回到主页
     */
    public void goMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}
