package com.example.showwebview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.showwebview.R;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

@Route(path = "/modulePublic/showwebview/X5WebViewActivity")
public class X5WebViewActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    LoadingAnimation loadingAnimation;
    WebView webView;

    @Autowired(name = "url")
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x5_web_view);
        ARouter.getInstance().inject(this);

        initWidget();
    }

    public void initWidget(){
        imageView = (ImageView) findViewById(R.id.loading);
        textView = (TextView) findViewById(R.id.text);
        loadingAnimation = new LoadingAnimation(imageView);

        webView = findViewById(R.id.X5WebView);

        webView.getSettings().setJavaScriptEnabled(true); // 开启js
        // 刘海屏适配
        if (webView.getSettingsExtension() != null) {
            webView.getSettingsExtension().setDisplayCutoutEnable(true);
        } else {
            // 如果 IX5WebSettingsExtension 为 null，采取备用方案
            // 例如使用系统默认的 WebView 设置
            //webView.getSettings().setDisplayZoomControls(false);
        }

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                loadingAnimation.ReleaseAnimation();
                imageView.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        });
        webView.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        QbSdk.clearAllWebViewCache(this, true); // 清除缓存
        super.onDestroy();
    }
}