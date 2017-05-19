package com.wuyixiong.interview.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecommedDetailActivity extends BaseActivity {

    @Bind(R.id.toolbar_recommend_details)
    Toolbar toolbar;
    @Bind(R.id.pb_recommend_details)
    ProgressBar pb;
    @Bind(R.id.wb_recommend_details)
    WebView wb;

    private String mUrl;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommed_detail);
        ButterKnife.bind(this);
        //设置toolbar的返回键
        toolbar.setNavigationIcon(R.drawable.toolbar_back_white);

        mUrl = getIntent().getStringExtra("url");
        position = getIntent().getIntExtra("position",0);

        //设置webview
        setWebView();

        setListener();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }

    /**
     * 设置webview
     */
    public void setWebView() {
        //web的一些参数设置
        WebSettings webSettings = wb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
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
}
