package com.example.showwebview.Entity;

import android.content.Context;

import org.mozilla.geckoview.GeckoRuntime;

public class GeckoRuntimeSingleton {
    private static GeckoRuntime instance;

    public static synchronized GeckoRuntime getInstance(Context context) {
        if (instance == null) {
            instance = GeckoRuntime.create(context);
        }
        return instance;
    }
}
