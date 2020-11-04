package com.github.kongpf8848.rxhttp.sample;

import android.app.Application;
import android.util.Log;

import com.github.kongpf8848.rxhttp.RxHttpConfig;
import com.kongpf.commonhelper.ToastHelper;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                e.printStackTrace();
                Log.e("Exception", e.getMessage());
            }
        });

        ToastHelper.init(this);
        RxHttpConfig.getInstance()
                .maxRetries(3)
                .retryDelayMillis(200)
                .debugMode(BuildConfig.DEBUG);


    }
}
