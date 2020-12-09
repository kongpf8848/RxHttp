package com.github.kongpf8848.rxhttp.sample

import android.app.Application
import com.github.kongpf8848.rxhttp.RxHttpConfig
import com.github.kongpf8848.rxhttp.sample.http.interceptor.MockInterceptor
import com.github.kongpf8848.rxhttp.sample.utils.LogUtils


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            e.printStackTrace()
            LogUtils.e("Exception", e.message)
        }

        LogUtils.init(this,BuildConfig.DEBUG)

        /**
         * 1.失败重试3次，每次间隔200毫秒
         * 2.添加拦截器MockInterceptor
         */
        RxHttpConfig.getInstance()
                .maxRetries(3)
                .retryDelayMillis(200)
                .debugMode(true)
                .builder.addInterceptor(MockInterceptor())
    }
}