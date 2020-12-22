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
        val config= RxHttpConfig.getInstance()
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

    //get请求
    fun <T> get(context: Context): GetRequest<T> {
        return GetRequest(context)
    }
    fun <T> get(activity: Activity): GetRequest<T> {
        return GetRequest(activity)
    }
    fun <T> get(fragment: Fragment): GetRequest<T> {
        return GetRequest(fragment)
    }

    //post请求
    fun <T> post(context: Context): PostRequest<T> {
        return PostRequest(context)
    }
    fun <T> post(activity: Activity): PostRequest<T> {
        return PostRequest(activity)
    }
    fun <T> post(fragment: Fragment): PostRequest<T> {
        return PostRequest(fragment)
    }

    //post表单请求
    fun <T> postForm(context: Context): PostFormRequest<T> {
        return PostFormRequest(context)
    }
    fun <T> postForm(activity: Activity): PostFormRequest<T> {
        return PostFormRequest(activity)
    }
    fun <T> postForm(fragment: Fragment): PostFormRequest<T> {
        return PostFormRequest(fragment)
    }

    //upload请求
    fun <T> upload(context: Context): UploadRequest<T> {
        return UploadRequest(context)
    }
    fun <T> upload(activity: Activity): UploadRequest<T> {
        return UploadRequest(activity)
    }
    fun <T> upload(fragment: Fragment): UploadRequest<T> {
        return UploadRequest(fragment)
    }

    //download请求
    fun  download(context: Context): DownloadRequest {
        return DownloadRequest(context)
    }
    fun  download(activity: Activity): DownloadRequest {
        return DownloadRequest(activity)
    }
    fun  download(fragment: Fragment): DownloadRequest {
        return DownloadRequest(fragment)
    }

    //put请求
    fun <T> put(context: Context): PutRequest<T> {
        return PutRequest(context)
    }
    fun <T> put(activity: Activity): PutRequest<T> {
        return PutRequest(activity)
    }
    fun <T> put(fragment: Fragment): PutRequest<T> {
        return PutRequest(fragment)
    }

    //delete请求
    fun <T> delete(context: Context): DeleteRequest<T> {
        return DeleteRequest(context)
    }
    fun <T> delete(activity: Activity): DeleteRequest<T> {
        return DeleteRequest(activity)
    }
    fun <T> delete(fragment: Fragment): DeleteRequest<T> {
        return DeleteRequest(fragment)
    }


    fun <T> enqueue(request: AbsRequest<T>, callback: HttpCallback<T>) {
        var observable: Observable<ResponseBody>? = null
        if (request is GetRequest) {
            observable = if(request.getParams()!=null) {
                httpService.get(request.url!!, request.getParams()!!)
            } else{
                httpService.get(request.url!!)
            }
        } else if (request is PutRequest) {
            observable = httpService.put(request.url!!, request.buildRequestBody()!!)
        } else if (request is UploadRequest) {
            observable = httpService.post(request.url!!, request.buildRequestBody()!!)
        } else if (request is PostRequest) {
            observable = httpService.post(request.url!!, request.buildRequestBody()!!)
        } else if (request is PostFormRequest) {
            observable = if(request.getParams()!=null){
                httpService.postForm(request.url!!, request.getParams()!!)
            } else{
                httpService.postForm(request.url!!)
            }
        } else if (request is DeleteRequest) {
            observable = if(request.getParams()!=null) {
                httpService.delete(request.url!!, request.getParams()!!)
            } else{
                httpService.delete(request.url!!)
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
                            var start: Long = 0
                            if (file.exists()) {
                                if (contentLength == -1L || file.length() >= contentLength) {
                                    file.delete()
                                } else {
                                    start = file.length()
                                }
                            }
                            Observable.just(start)
                        }.flatMap { start -> httpService.download(request.url!!, String.format("bytes=%d-", start)) }.subscribeOn(Schedulers.io())
            } else {
                observable = httpService.download(request.url!!)
            }
        }
        if (observable != null) {
            val observableFinal= observable.map(Function<ResponseBody,T> { body ->
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

    fun cancelTag(tag: Any?) {
        RxHttpTagManager.getInstance().cancelTag(tag)
    }

    companion object {
        fun getInstance()= RxHttp.holder
    }

}