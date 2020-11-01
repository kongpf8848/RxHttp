package com.github.kongpf8848.rxhttp.sample;

import android.app.Application;
import android.util.Log;

import com.github.kongpf8848.rxhttp.HttpConfig;
import com.kongpf.commonhelper.ToastHelper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                e.printStackTrace();
                Log.d("RxHttp Exception",e.getMessage());
            }
        });

        ToastHelper.init(this);
        //初始化RxHttp
        OkHttpClient.Builder builder= new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        HttpConfig.getInstance().builder(builder)
                .logEnable(BuildConfig.DEBUG)
                .logTag(Constants.LOG_TAG)
                .builder(new OkHttpClient.Builder().addInterceptor(loggingInterceptor))
                .retryDelayMillis(300)
                .maxRetries(3);


    }
}
