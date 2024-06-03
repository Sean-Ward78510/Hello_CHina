package com.example.nihaochina;

import android.app.Application;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import org.mozilla.geckoview.GeckoRuntime;

import java.util.HashMap;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG){
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        initX5Web();
    }

    public void initX5Web(){
// 搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.setDownloadWithoutWifi(true);
        // 在调用TBS初始化、创建WebView之前进行如下配置
        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                // x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("BaseApplication", "onViewInitFinished:  loading X5 " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                Log.d("BaseApplication", "onViewInitFinished:  loading X5 ");
            }
        };
        // x5内核初始化接口
        QbSdk.initX5Environment(this, cb);
    }
}
