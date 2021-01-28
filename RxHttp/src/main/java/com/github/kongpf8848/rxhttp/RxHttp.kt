package com.github.kongpf8848.rxhttp

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.github.kongpf8848.rxhttp.bean.DownloadInfo
import com.github.kongpf8848.rxhttp.callback.DownloadCallback
import com.github.kongpf8848.rxhttp.callback.HttpCallback
import com.github.kongpf8848.rxhttp.converter.DownloadConverter
import com.github.kongpf8848.rxhttp.converter.GsonConverter
import com.github.kongpf8848.rxhttp.request.*
import com.github.kongpf8848.rxhttp.util.Md5Util
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File

class RxHttp private constructor() {

    private object RxHttp {
        val holder = RxHttp()
    }

    private val okHttpClient: OkHttpClient
    private val retrofit: Retrofit
    private val httpService: HttpService

    init {
        val config = RxHttpConfig.getInstance()
        val builder = config.getBuilder()
        okHttpClient = builder.build()
        retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://example.com")
                .build()
        httpService = retrofit.create(HttpService::class.java)
    }

    //GET请求
    fun get(context: Context): GetRequest {
        return GetRequest(context)
    }

    fun get(activity: Activity): GetRequest {
        return GetRequest(activity)
    }

    fun get(fragment: Fragment): GetRequest {
        return GetRequest(fragment)
    }

    //POST请求
    fun post(context: Context): PostRequest {
        return PostRequest(context)
    }

    fun post(activity: Activity): PostRequest {
        return PostRequest(activity)
    }

    fun post(fragment: Fragment): PostRequest {
        return PostRequest(fragment)
    }

    //POST FORM请求
    fun postForm(context: Context): PostFormRequest {
        return PostFormRequest(context)
    }

    fun postForm(activity: Activity): PostFormRequest {
        return PostFormRequest(activity)
    }

    fun postForm(fragment: Fragment): PostFormRequest {
        return PostFormRequest(fragment)
    }

    //Upload请求
    fun upload(context: Context): UploadRequest {
        return UploadRequest(context)
    }

    fun upload(activity: Activity): UploadRequest {
        return UploadRequest(activity)
    }

    fun upload(fragment: Fragment): UploadRequest {
        return UploadRequest(fragment)
    }

    //Download请求
    fun download(context: Context): DownloadRequest {
        return DownloadRequest(context)
    }

    fun download(activity: Activity): DownloadRequest {
        return DownloadRequest(activity)
    }

    fun download(fragment: Fragment): DownloadRequest {
        return DownloadRequest(fragment)
    }

    //PUT请求
    fun put(context: Context): PutRequest {
        return PutRequest(context)
    }

    fun put(activity: Activity): PutRequest {
        return PutRequest(activity)
    }

    fun put(fragment: Fragment): PutRequest {
        return PutRequest(fragment)
    }

    //DELETE请求
    fun delete(context: Context): DeleteRequest {
        return DeleteRequest(context)
    }

    fun delete(activity: Activity): DeleteRequest {
        return DeleteRequest(activity)
    }

    fun delete(fragment: Fragment): DeleteRequest {
        return DeleteRequest(fragment)
    }

    fun <T> enqueue(request: AbsRequest, callback: HttpCallback<T>) {
        var observable: Observable<ResponseBody>? = null
        if (request is GetRequest) {
            observable = if (request.getParams() != null) {
                httpService.get(request.url, request.getParams()!!)
            } else {
                httpService.get(request.url)
            }
        } else if (request is PutRequest) {
            observable = httpService.put(request.url, request.buildRequestBody()!!)
        } else if (request is UploadRequest) {
            observable = httpService.post(request.url, request.buildRequestBody()!!)
        } else if (request is PostRequest) {
            observable = httpService.post(request.url, request.buildRequestBody()!!)
        } else if (request is PostFormRequest) {
            observable = if (request.getParams() != null) {
                httpService.postForm(request.url, request.getParams()!!)
            } else {
                httpService.postForm(request.url)
            }
        } else if (request is DeleteRequest) {
            observable = if (request.getParams() != null) {
                httpService.delete(request.url, request.getParams()!!)
            } else {
                httpService.delete(request.url)
            }
        } else if (request is DownloadRequest) {
            val file = File(request.dir, request.filename)
            val downloadInfo = DownloadInfo(request.url, request.dir, request.filename)
            if (!TextUtils.isEmpty(request.md5)) {
                if (file.exists()) {
                    val fileMd5 = Md5Util.getMD5(file)
                    if (request.md5.equals(fileMd5, ignoreCase = true)) {
                        downloadInfo.total = file.length()
                        downloadInfo.progress = file.length()
                        callback.onNext(downloadInfo as T)
                        return
                    }
                }
            }
            if (request.isBreakpoint) {
                observable = Observable.just(request.url)
                        .flatMap { url ->
                            val contentLength = getContentLength(url)
                            var startPosition: Long = 0
                            if (file.exists()) {
                                if (contentLength == -1L || file.length() >= contentLength) {
                                    file.delete()
                                } else {
                                    startPosition = file.length()
                                }
                            }
                            Observable.just(startPosition)
                        }.flatMap { position -> httpService.download(request.url, String.format("bytes=%d-", position)) }.subscribeOn(Schedulers.io())
            } else {
                observable = httpService.download(request.url)
            }
        }
        if (observable != null) {
            val observableFinal = observable.map(Function<ResponseBody, T> { body ->
                if (request is DownloadRequest) {
                    val downloadConverter: DownloadConverter<T> = DownloadConverter(request, callback as DownloadCallback)
                    downloadConverter.convert(body, callback.type)
                } else {
                    GsonConverter<T>().convert(body, callback.type)
                }
            }).retryWhen(RetryWithDelay(RxHttpConfig.getInstance().maxRetries, RxHttpConfig.getInstance().retryDelayMillis))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            if (request.context is LifecycleOwner) {
                val lifecycleOwner = request.context as LifecycleOwner
                observableFinal.`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, Lifecycle.Event.ON_DESTROY)))
                        .subscribe(HttpObserver(callback, request.getTag()))
            } else {
                observableFinal.subscribeWith(HttpObserver(callback, request.getTag()))
            }
        }
    }

    @Throws(Exception::class)
    private fun getContentLength(url: String): Long {
        val response = httpService.head(url).execute()
        if (response.isSuccessful) {
            val contentLength = response.headers()["Content-Length"]
            if (!TextUtils.isEmpty(contentLength)) {
                return contentLength!!.toLong()
            }
        }
        return -1
    }

    /**
     * 取消网络请求,tag为null取消所有网络请求,tag不为null值取消指定的网络请求
     */
    fun cancelRequest(tag: Any?) {
        RxHttpTagManager.getInstance().cancelTag(tag)
    }

    companion object {
        fun getInstance() = RxHttp.holder
    }

}