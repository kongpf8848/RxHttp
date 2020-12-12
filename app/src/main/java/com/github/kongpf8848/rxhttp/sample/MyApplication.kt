package com.github.kongpf8848.rxhttp.sample

import android.app.Application
import com.github.kongpf8848.rxhttp.RxHttpConfig
import com.github.kongpf8848.rxhttp.sample.http.interceptor.MockInterceptor
import com.github.kongpf8848.rxhttp.sample.utils.LogUtils
import com.kongpf.commonhelper.ToastHelper
import okhttp3.logging.HttpLoggingInterceptor


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            e.printStackTrace()
            LogUtils.e("Exception", e.message)
        }
        LogUtils.init(this,BuildConfig.DEBUG)
        ToastHelper.init(this)

        /**
         * 对RxHttp进行配置，可选
         * 失败重试3次，每次间隔200毫秒
         * 添加日志打印拦截器HttpLoggingInterceptor
         * 添加模拟网络请求拦截器MockInterceptor
         */
        RxHttpConfig.getInstance()
                .maxRetries(3)
                .retryDelayMillis(200)
                .builder.addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .addInterceptor(MockInterceptor())
    }
}