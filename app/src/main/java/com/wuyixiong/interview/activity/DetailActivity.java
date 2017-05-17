package com.wuyixiong.interview.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.base.BaseActivity;
import com.wuyixiong.interview.entity.News;
import com.wuyixiong.interview.entity.User;
import com.wuyixiong.interview.event.CollectEvent;
import com.wuyixiong.interview.event.IsCollection;
import com.wuyixiong.interview.event.SendError;
import com.wuyixiong.interview.event.SendNews;
import com.wuyixiong.interview.utils.CollectionOperate;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class DetailActivity extends BaseActivity implements View.OnClickListener {

    private WebView wb;

    private String mUrl;
    private News news;

    private Toolbar toolbar;
    private ProgressBar pb;
    private ImageView menu;
    private View v;//popwindow的view
    private PopupWindow pp;
    private Button ppCollection;
    private Button ppShare;
    private Button ppCancel;
    private SharedPreferences shared;
    private String newsCollectionId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mUrl = getIntent().getStringExtra("url");
        news = (News) getIntent().getSerializableExtra("news");

        shared = getSharedPreferences("collectionId", MODE_PRIVATE);
        newsCollectionId = shared.getString("newsCollectionId", "null");

        initView();
        //设置toolbar的返回键
        toolbar.setNavigationIcon(R.drawable.toolbar_back_white);
        //设置webview
        setWebView();
        setListener();
        //判断该条是否已经收藏
        CollectionOperate.getInstance().isNewsCollection(newsCollectionId, news.getObjectId());

//        Log.i("tag", "---------------------"+shared.getString("newsCollectionId","null"));
//        Log.i("tag", "---------------------"+shared.getString("questionCollectionId","null"));
    }

    @Override
    protected void initView() {
        wb = (WebView) findViewById(R.id.wb_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar_details);
        pb = (ProgressBar) findViewById(R.id.pb_details);
        menu = (ImageView) findViewById(R.id.iv_menu_detail);
        pp = new PopupWindow();
        initPopupWindow();

        //web的一些参数设置
        WebSettings webSettings = wb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    /**
     * 初始化popupWindow
     */
    private void initPopupWindow() {
        v = getLayoutInflater().inflate(R.layout.popup_details, null);
        pp.setContentView(v);
        pp.setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        pp.setFocusable(true);
        pp.setTouchable(true);
        pp.setOutsideTouchable(true);
        pp.setBackgroundDrawable(new BitmapDrawable());
        ppCancel = (Button) v.findViewById(R.id.btn_details_popup_cancel);
        ppCollection = (Button) v.findViewById(R.id.btn_details_popup_favorite);
        ppShare = (Button) v.findViewById(R.id.btn_details_popup_share);
        ppCollection.setOnClickListener(this);
        ppCancel.setOnClickListener(this);
        ppShare.setOnClickListener(this);

    }

    @Override
    protected void setListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        menu.setOnClickListener(this);
    }

    /**
     * 设置webview
     */
    public void setWebView() {
        //设置网页在本应用打开
        wb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                view.loadUrl("file:///android_asset/404.htm");
            }
        });
        wb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    pb.setVisibility(View.GONE);
                } else {
                    pb.setProgress(newProgress);
                }
            }
        });
        wb.loadUrl(mUrl);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wb.canGoBack()) {
            wb.goBack();
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void success(CollectEvent event) {
        //收藏成功
        if (event.isCollected()) {
            cancelDialog();
            ppCollection.setText("取消收藏");
            toast("收藏成功");
        } else {
            cancelDialog();
            ppCollection.setText("收藏");
            toast("取消成功");
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void failed(SendError event) {
        //收藏失败或取消失败
        if (event.getErrorId() == 2) {
            cancelDialog();
            toast(event.getErrorText());
        } else if (event.getErrorId() == 3) {
            cancelDialog();
            toast(event.getErrorText());
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void checked(IsCollection event) {
        //判断是否已经收藏
        if (event.isCollected()) {
            ppCollection.setText("取消收藏");
        } else {
            ppCollection.setText("收藏");
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_menu_detail:
                if (BmobUser.getCurrentUser(User.class) == null) {
                    toast("请先登录");
                } else {
                    if (pp != null && pp.isShowing()) {
                        pp.dismiss();
                    } else {
                        pp.showAsDropDown(toolbar);
                    }
                }
                break;
            case R.id.btn_details_popup_favorite:
                if (ppCollection.getText().toString().equals("收藏")) {
                    CollectionOperate.getInstance().addNewsCollection(newsCollectionId, news);
                } else if (ppCollection.getText().toString().equals("取消收藏")) {
                    CollectionOperate.getInstance().removeNewsCollection(newsCollectionId, news);
                }
                pp.dismiss();
                showLoadingDialog("稍等", true);
                break;
            case R.id.btn_details_popup_share:
                break;
            case R.id.btn_details_popup_cancel:
                pp.dismiss();
                break;
        }
    }
}
