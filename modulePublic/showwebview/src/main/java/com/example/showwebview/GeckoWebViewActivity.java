package com.example.showwebview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.showwebview.R;
import com.example.showwebview.Entity.GeckoRuntimeSingleton;

import org.mozilla.geckoview.GeckoRuntime;
import org.mozilla.geckoview.GeckoSession;
import org.mozilla.geckoview.GeckoSessionSettings;
import org.mozilla.geckoview.GeckoView;

@Route(path = "/modulePublic/showwebview/GeckoWebViewActivity")
public class GeckoWebViewActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    LoadingAnimation loadingAnimation;
    GeckoView webView;
    GeckoSession session;
    GeckoRuntime runtime;

    @Autowired(name = "url")
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gecko_web_view);
        ARouter.getInstance().inject(this);

        initWidget();
    }

    public void initWidget() {
        imageView = (ImageView) findViewById(R.id.loading);
        textView = (TextView) findViewById(R.id.text);
        loadingAnimation = new LoadingAnimation(imageView);

        webView = findViewById(R.id.geckoView);
        session = new GeckoSession();
        runtime = GeckoRuntimeSingleton.getInstance(this);

        session.open(runtime);
        GeckoSessionSettings settings = session.getSettings();
        settings.setAllowJavascript(true);
        session.setProgressDelegate(new GeckoSession.ProgressDelegate() {
            @Override
            public void onPageStop(@NonNull GeckoSession session, boolean success) {
                GeckoSession.ProgressDelegate.super.onPageStop(session, success);
                if (success){
                    loadingAnimation.ReleaseAnimation();
                    imageView.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
                }
            }
        });
        webView.setSession(session);
        Log.d("GeckoView", "initWidget: " + url);
        session.loadUri(url);
    }
}