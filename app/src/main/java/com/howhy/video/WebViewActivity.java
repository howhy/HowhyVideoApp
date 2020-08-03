package com.howhy.video;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import java.lang.reflect.Method;

public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = "WebViewActivity";
    private WebView wvBookPlay;
    private FrameLayout flVideoContainer;
    private PowerManager powerManager;
    private PowerManager.WakeLock mWakeLock;
    private String title;
    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        powerManager = (PowerManager)getSystemService(POWER_SERVICE);
        if (powerManager != null) {
            mWakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "WakeLock");
        }
        initView();
        String url =getIntent().getStringExtra("src");
        String[] urlArrs=url.split("\\$");
        Log.i(TAG, "onCreate: "+url);
         //"https://bilibili.com-l-163.com/share/2722551363e0df6fe8dd2f3933642d78";
        title=urlArrs[0];
        initData(urlArrs[1]);
    }

    private void initData(String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("android");
        cookieManager.setCookie(url, stringBuffer.toString());
        cookieManager.setAcceptCookie(true);
        wvBookPlay.loadUrl(url);
    }

    private void initView() {
        wvBookPlay=findViewById(R.id.wvBookPlay);
        flVideoContainer=findViewById(R.id.flVideoContainer);
        wvBookPlay.getSettings().setJavaScriptEnabled(true);
        wvBookPlay.getSettings().setUseWideViewPort(true);
        wvBookPlay.getSettings().setLoadWithOverviewMode(true);
        wvBookPlay.getSettings().setAllowFileAccess(true);
        wvBookPlay.getSettings().setSupportZoom(true);
        wvBookPlay.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        try {
            if (Build.VERSION.SDK_INT >= 16) {
                Class<?> clazz = wvBookPlay.getSettings().getClass();
                Method method = clazz.getMethod("setAllowUniversalAccessFromFileURLs", boolean.class);
                if (method != null) {
                    method.invoke(wvBookPlay.getSettings(), true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        wvBookPlay.getSettings().setPluginState(WebSettings.PluginState.ON);
        wvBookPlay.getSettings().setDomStorageEnabled(true);// 必须保留，否则无法播放优酷视频，其他的OK
        wvBookPlay.setWebChromeClient(new MyWebChromeClient());// 重写一下，有的时候可能会出现问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wvBookPlay.getSettings().setMixedContentMode(wvBookPlay.getSettings().MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        switch (config.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                break;
        }
    }

    private class MyWebChromeClient extends WebChromeClient{
        CustomViewCallback mCallback;
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            fullScreen();
            wvBookPlay.setVisibility(View.GONE);
            flVideoContainer.setVisibility(View.VISIBLE);
            flVideoContainer.addView(view);
            mCallback = callback;
            super.onShowCustomView(view, callback);
        }

        @Override
        public void onHideCustomView() {
            fullScreen();
            wvBookPlay.setVisibility(View.VISIBLE);
            flVideoContainer.setVisibility(View.GONE);
            flVideoContainer.removeAllViews();
            super.onHideCustomView();
        }
    }

    private void fullScreen() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mWakeLock != null) mWakeLock.acquire();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWakeLock != null) mWakeLock.release();
    }
    @Override
    protected void onDestroy() {
        if (wvBookPlay != null) {
            wvBookPlay.destroy();
        }
        super.onDestroy();
    }

}