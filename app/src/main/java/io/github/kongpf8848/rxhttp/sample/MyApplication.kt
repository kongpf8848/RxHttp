package io.github.kongpf8848.rxhttp.sample

import android.app.Application
import com.kongpf.commonhelper.ToastHelper
import io.github.kongpf8848.rxhttp.FixHttpLoggingInterceptor
import io.github.kongpf8848.rxhttp.RxHttpConfig
import io.github.kongpf8848.rxhttp.sample.http.interceptor.MockInterceptor
import io.github.kongpf8848.rxhttp.sample.utils.LogUtils
import java.util.concurrent.TimeUnit


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            e.printStackTrace()
            LogUtils.e("Crash", e.message)
        }
        LogUtils.init(this, BuildConfig.DEBUG)
        ToastHelper.init(this)

        /**
         * 对RxHttp进行配置，可选
         */
        RxHttpConfig.getInstance()
                /**
                 * 失败重试次数
                 */
                .maxRetries(3)
                /**
                 * 每次失败重试间隔时间
                 */
                .retryDelayMillis(200)
                /**
                 * 证书校验，单向校验，即客户端校验服务端证书，null则为不校验
                 */
                //.certificate(AssetUtils.openFile(applicationContext,"xxx.cer"))
                /**
                 * 设置OkHttpClient.Builder(),RxHttp支持自定义OkHttpClient.Builder()
                 */
                .getBuilder().apply {
                    connectTimeout(60, TimeUnit.SECONDS)
                    readTimeout(60, TimeUnit.SECONDS)
                    writeTimeout(60, TimeUnit.SECONDS)
                    /**
                     * DEBUG模式下,添加日志拦截器,建议使用RxHttp中的FixHttpLoggingInterceptor,使用OkHttp的HttpLoggingInterceptor在上传下载的时候会有问题
                     */
                    if (BuildConfig.DEBUG) {
                        addInterceptor(FixHttpLoggingInterceptor().apply {
                            level = FixHttpLoggingInterceptor.Level.BODY
                        })
                        addInterceptor(MockInterceptor())
                    }
                }

    }
}