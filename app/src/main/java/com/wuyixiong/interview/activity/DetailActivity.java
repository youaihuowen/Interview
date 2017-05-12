package com.wuyixiong.interview.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.base.BaseActivity;

public class DetailActivity extends BaseActivity {

    private WebView wb;
    private String mUrl;
    private Toolbar toolbar;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mUrl = getIntent().getStringExtra("url");
        initView();
        //设置toolbar的返回键
        toolbar.setNavigationIcon(R.drawable.toolbar_back_white);
        //设置webview
        setWebView();
        setListener();
    }

    @Override
    protected void initView() {
        wb = (WebView) findViewById(R.id.wb_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar_details);
        pb = (ProgressBar) findViewById(R.id.pb_details);

        //web的一些参数设置
        WebSettings webSettings = wb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
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

    /**
     * 设置webview
     */
    public void setWebView(){
        //设置网页在本应用打开
        wb.setWebViewClient(new WebViewClient(){
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
        wb.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100){
                    pb.setVisibility(View.GONE);
                }else {
                    pb.setProgress(newProgress);
                }
            }
        });
        wb.loadUrl(mUrl);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wb.canGoBack()){
            wb.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
