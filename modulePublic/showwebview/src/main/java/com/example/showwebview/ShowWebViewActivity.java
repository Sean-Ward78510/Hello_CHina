package com.example.showwebview;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.showwebview.R;
import com.example.showwebview.Entity.Utils;
import com.example.tool.Util.SERVER_IP;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

@Route(path = "/modulePublic/showwebview/ShowWebViewActivity")
public class ShowWebViewActivity extends AppCompatActivity implements  View.OnClickListener{
    private String URL = SERVER_IP.Server_IP;
    ImageView imageView;
    TextView textView;
    LoadingAnimation loadingAnimation;
    LinearLayout optionBar;
    ImageView like;
    ImageView college;
    ImageView transmit;

    boolean isLike;
    boolean isCollege;

    @Autowired(name = "url")
    String url;
    @Autowired(name = "isArticle")
    boolean isArticle;

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_web_view);
        ARouter.getInstance().inject(this);

        initWidget();
    }

    public void initWidget(){
        like = (ImageView) findViewById(R.id.like);
        college = (ImageView) findViewById(R.id.college);
        transmit = (ImageView) findViewById(R.id.transmit);

        like.setOnClickListener(this);
        college.setOnClickListener(this);
        transmit.setOnClickListener(this);

        imageView = (ImageView) findViewById(R.id.loading);
        textView = (TextView) findViewById(R.id.text);
        optionBar = (LinearLayout) findViewById(R.id.optionBar);
        loadingAnimation = new LoadingAnimation(imageView);

        webView = (WebView) findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);//启用WebView的JavaScript执行功能，让WebView能够运行网页中的JavaScript代码。

        webView.getSettings().setDomStorageEnabled(true);//启用DOM存储API，支持网页使用localStorage来存储数据。

        webView.getSettings().setDatabaseEnabled(true);//启用数据库存储API，支持网页使用Web SQL数据库存储数据。

        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);//允许混合内容，即允许HTTPS页面加载HTTP资源。

        //webView.getSettings().setUseWideViewPort(true);//设置WebView是否支持使用宽视窗，使页面按实际尺寸显示。


        //webView.getSettings().setLoadWithOverviewMode(true);//设置WebView是否以overview模式加载页面，即缩小内容以适应屏幕宽度。
        // 是否支持缩放，默认为true
        //webView.getSettings().setSupportZoom(false);//设置WebView是否支持缩放，默认为true。
        // 是否使用内置的缩放控件
        //webView.getSettings().setBuiltInZoomControls(false);//设置WebView是否使用内置的缩放控件。

        webView.setWebViewClient(new MyWebViewClient());

        // 清缓存和记录，缓存引起的白屏
        webView.clearCache(true);
        webView.clearHistory();
        //... 有很多clear的方法

        webView.loadUrl(url);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.like){
            if (isLike){
                isLike = false;
                sendDisikeRequire();
                like.setImageResource(R.drawable.article_dislike);
                Toast.makeText(ShowWebViewActivity.this,"取消点赞成功！我们会加倍努力",Toast.LENGTH_SHORT).show();
            }else {
                isLike = true;
                sendLikeRequire();
                like.setImageResource(R.drawable.article_like);
                Toast.makeText(ShowWebViewActivity.this,"点赞成功！",Toast.LENGTH_SHORT).show();
            }
        }
        if (view.getId() == R.id.college){
            if (isCollege){
                isCollege = false;
                sendDisCllegeRequire();
                college.setImageResource(R.drawable.article_discollect);
                Toast.makeText(ShowWebViewActivity.this,"取消收藏成功！我们会加倍努力",Toast.LENGTH_SHORT).show();
            }else {
                isCollege = true;
                sendCollegeRequire();
                college.setImageResource(R.drawable.article_collect);
                Toast.makeText(ShowWebViewActivity.this,"收藏成功！",Toast.LENGTH_SHORT).show();
            }
        }
        if (view.getId() == R.id.transmit){
            Toast.makeText(ShowWebViewActivity.this,"转发成功！",Toast.LENGTH_SHORT).show();
        }
    }

    class MyWebViewClient extends WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url) {
            loadingAnimation.ReleaseAnimation();
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            if (isArticle){
                optionBar.setVisibility(View.VISIBLE);
                Log.d("WEBVIEW", "onPageFinished: "+isArticle);
            }
        }
//        @Override
//        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//            handler.proceed();
//            super.onReceivedSslError(view, handler, error);
//        }
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            view.loadUrl(request.getUrl().toString());
//            return true;
//        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (webView.canGoBack()){
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    public void sendLikeRequire(){
        Utils.Like(URL, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseDate = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseDate);
                    if (jsonObject.getInt("code") == 200){
                        Log.d("TAG", "onResponse: 成功！");
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void sendDisikeRequire(){
        Utils.DisLike(URL, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseDate = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseDate);
                    if (jsonObject.getInt("code") == 200){
                        Log.d("TAG", "onResponse: 成功！");
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void sendCollegeRequire(){
        Utils.College(URL, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseDate = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseDate);
                    if (jsonObject.getInt("code") == 200){
                        Log.d("TAG", "onResponse: 成功！");
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void sendDisCllegeRequire(){
        Utils.DisCollege(URL, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseDate = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseDate);
                    if (jsonObject.getInt("code") == 200){
                        Log.d("TAG", "onResponse: 成功！");
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}