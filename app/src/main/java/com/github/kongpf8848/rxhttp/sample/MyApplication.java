package com.github.kongpf8848.rxhttp.sample;

import android.app.Application;
import com.github.kongpf8848.rxhttp.HttpConfig;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化RxHttp
        OkHttpClient.Builder builder= new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        HttpConfig.getInstance().builder(builder).debug(BuildConfig.DEBUG);

    }
}
