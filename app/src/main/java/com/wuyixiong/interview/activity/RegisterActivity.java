package com.wuyixiong.interview.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.base.BaseActivity;
import com.wuyixiong.interview.entity.NewsCollection;
import com.wuyixiong.interview.entity.QuestionCollection;
import com.wuyixiong.interview.entity.User;
import com.wuyixiong.interview.utils.LoginedSetId;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.edit_name_register)
    EditText account;
    @Bind(R.id.edit_verification_register)
    EditText verification;
    @Bind(R.id.tv_getverification)
    TextView getverification;
    @Bind(R.id.edit_pwd_register)
    EditText pwd;
    @Bind(R.id.edit_nickname_register)
    EditText nickname;
    @Bind(R.id.btn_register)
    Button register;
    private Toolbar toolbar;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mContext = this;
        initView();
        //设置toolbar的返回键
        toolbar.setNavigationIcon(R.drawable.toolbar_back_white);
        setListener();
    }

    @Override
    protected void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    protected void setListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @OnClick({R.id.tv_getverification, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_getverification:
                if (account.getText().toString() == null || account.getText().toString().equals("")) {
                    toast("号码不能为空");
                } else if (account.getText().toString().length() != 11){
                    toast("请输入有效的11位号码");
                }else {

                    //1、调用请求验证码接口
                    BmobSMS.requestSMSCode(account.getText().toString(), "短信模板", new QueryListener<Integer>() {

                        @Override
                        public void done(Integer smsId, BmobException ex) {
                            if (ex == null) {//验证码发送成功
                                toast("验证码发送成功，请尽快使用");
                                Log.i("tag", "done: "+smsId);
                                getverification.setEnabled(false);
                                getverification.setBackground(getResources().getDrawable(R.drawable.shap_corner_yanzheng));
                                timer.start();
                            }else {
                                toast("验证码发送失败，请检查网络连接");
                            }
                        }
                    });
                }

                break;
            case R.id.btn_register:
                if (account.getText().toString().length() !=11 || pwd.getText().toString().length() == 0){
                    toast("用户名密码填写不正确");
                }else {
                    BmobSMS.verifySmsCode(account.getText().toString(),
                            verification.getText().toString(),
                            new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e==null){
                                        registerAccount(account.getText().toString().trim(),
                                                nickname.getText().toString().trim(),
                                                pwd.getText().toString().trim());
                                    }else {
                                        toast("验证码不正确");
                                    }
                                }
                            });

                }
//                registerAccount(account.getText().toString().trim(),
//                        nickname.getText().toString().trim(),
//                        pwd.getText().toString().trim());
                break;
        }
    }

    /**
     * 用户注册的逻辑实现
     *
     * @param account  账号
     * @param nickName 昵称
     * @param password 密码
     */
    public void registerAccount(final String account, final String nickName, final String password) {
        if (account.equals("") || nickName.equals("") || password.equals("")) {
            toast("数据没有添全");
        } else {
            showLoadingDialog("注册中", true);
            User user = new User();
            user.setMobilePhoneNumber(account);
            user.setUsername(account);
            user.setNickName(nickName);
            user.setPassword(password);
            user.signUp(new SaveListener<User>() {
                @Override
                public void done(User user, BmobException e) {
                    if (e == null) {
                        LoginedSetId lsid = new LoginedSetId(mContext);
                        lsid.setCollectionTable(user.getObjectId());
                        registed(account, nickName, password);
                        Log.i("tag", "------------注册成功");
                    } else {
                        cancelDialog();
                        toast(e.toString());
                        Log.i("tag", e.toString());
                    }
                }
            });

        }
    }

    /**
     * 验证码倒计时
     */
    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long l) {
            getverification.setText(l / 1000 + "秒后重新获取");
        }

        @Override
        public void onFinish() {
            getverification.setText("重新获取");
            getverification.setBackground(getResources().getDrawable(R.drawable.shap_corner));
            getverification.setEnabled(true);
        }
    };

    /**
     * 注册成功后执行的方法
     */
    public void registed(String account, String nickName, String password) {

        User userLogin = new User();
        userLogin.setUsername(account);
        userLogin.setPassword(password);
        userLogin.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
//                    Log.i("tag", "-------------登录成功");
                    cancelDialog();
                    //给登录界面返回结果码使其一起关闭
//                    setResult(FINISH_TOGETHER);
//                    finish();
                    goMainActivity();

                } else {
                    toast("注册成功但登录失败");
                    Log.i("tag", "-------------注册成功但登录失败");
                    cancelDialog();
                }
            }
        });

    }

    /**
     * 跳回到主页
     */
    public void goMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
