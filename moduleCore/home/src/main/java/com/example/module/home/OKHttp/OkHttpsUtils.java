package com.example.module.home.OKHttp;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkHttpsUtils {
    public static void getArticle(String url,String email,String article_url, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("email",email)
                .add("url",article_url)
                .build();

        // 构建请求，设置URL和POST请求方式，同时将请求体添加到请求中
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        // 发送请求并设置回调
        client.newCall(request).enqueue(callback);
    }

    public static void getNew(String url,okhttp3.Callback callback){
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
    public static void getLifeNew(String url,okhttp3.Callback callback){
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
    public static void getTravel(String url,okhttp3.Callback callback){
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
