package com.wuyixiong.interview.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wuyixiong.interview.R;

public abstract class BaseActivity extends FragmentActivity {

    public static final int FINISH_TOGETHER = 0;
    public static final int LOGOUT_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    public Dialog dialog;
    //初始化控件
    protected abstract void initView();
    //设置监听
    protected abstract void setListener();

    /**
     * 显示加载圆圈
     * @param msg  显示的文字
     * @param cancelable 显示时点击屏幕或返回键Dialog是否消失
     */
    public void showLoadingDialog(String msg,boolean cancelable) {
        View v = getLayoutInflater().inflate(R.layout.loading, null);
        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.rl_loading);
        ImageView im = (ImageView) v.findViewById(R.id.iv_loading);
        TextView tv = (TextView) v.findViewById(R.id.tv_loading);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.loading);
        im.setAnimation(animation);
        if(msg!=null){
            tv.setText(msg);
        }
        dialog = new Dialog(this,R.style.loading_dialog);
        dialog.setCancelable(cancelable);
        dialog.setContentView(layout);
        dialog.show();
    }

    /**
     * 取消加载圆圈
     */
    public void cancelDialog(){
        if(dialog != null){
            dialog.dismiss();
        }
    }

    /**
     * 弹吐司
     * @param msg
     */
    public void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 检测当的网络（WLAN、3G/2G）状态
     * @param context Context
     * @return true 表示网络可用
     */
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

}
