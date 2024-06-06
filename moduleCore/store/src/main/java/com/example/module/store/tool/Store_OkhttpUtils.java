package com.example.module.store.tool;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @ClassName shore_OkhttpUtils
 * @Description TODO
 * @Author JK_Wei
 * @Date 2024-06-06
 * @Version 1.0
 */

public class Store_OkhttpUtils {
    public static void getBrandList(String url,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .build();

        // 构建请求，设置URL和POST请求方式，同时将请求体添加到请求中
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        // 发送请求并设置回调
        //client.newCall(request).enqueue(callback);
    }
    public static void getStoreList(String url,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .build();

        // 构建请求，设置URL和POST请求方式，同时将请求体添加到请求中
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        // 发送请求并设置回调
        //client.newCall(request).enqueue(callback);
    }
}