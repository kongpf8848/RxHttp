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
import io.reactivex.ObservableSource
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

    //get请求
    fun <T >get(context: Context): GetRequest<T> {
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
            if(request.getParams()!=null) {
                observable = httpService.get(request.url!!, request.getParams()!!)
            }
            else{
                observable=httpService.get(request.url!!)
            }
        } else if (request is PutRequest) {
            val putRequest = request
            observable = httpService.put(putRequest.url!!, putRequest.buildRequestBody()!!)
        } else if (request is UploadRequest) {
            val uploadRequest = request
            observable = httpService.post(uploadRequest.url!!, uploadRequest.buildRequestBody()!!)
        } else if (request is PostRequest) {
            val postRequest = request
            observable = httpService.post(postRequest.url!!, postRequest.buildRequestBody()!!)
        } else if (request is PostFormRequest) {
            if(request.getParams()!=null){
                observable = httpService.postForm(request.url!!, request.getParams()!!)
            }
            else{
                observable = httpService.postForm(request.url!!)
            }

        } else if (request is DeleteRequest) {
            if(request.getParams()!=null) {
                observable = httpService.delete(request.url!!, request.getParams()!!)
            }
            else{
                observable=httpService.delete(request.url!!)
            }
        } else if (request is DownloadRequest) {
            val downloadRequest = request
            val file = File(downloadRequest.dir, downloadRequest.filename)
            val downloadInfo = DownloadInfo(downloadRequest.url, downloadRequest.dir, downloadRequest.filename)
            if (!TextUtils.isEmpty(downloadRequest.md5)) {
                if (file.exists()) {
                    val fileMd5 = Md5Util.getMD5(file)
                    if (downloadRequest.md5.equals(fileMd5, ignoreCase = true)) {
                        downloadInfo.total = file.length()
                        downloadInfo.progress = file.length()
                        callback.onNext(downloadInfo as T)
                        return
                    }
                }
            }
            if (downloadRequest.isBreakpoint) {
                observable = Observable.just(downloadRequest.url)
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
                        }.flatMap(object : Function<Long?, ObservableSource<ResponseBody>?> {
                            @Throws(Exception::class)
                            override fun apply(start: Long): ObservableSource<ResponseBody>? {
                                return httpService.download(downloadRequest.url!!, String.format("bytes=%d-", start))
                            }
                        }).subscribeOn(Schedulers.io())
            } else {
                observable = httpService.download(request.url!!)
            }
        }
        if (observable != null) {
            val observableFinal: Observable<T> = observable.map(object : Function<ResponseBody?, T> {
                @Throws(Exception::class)
                override fun apply(body: ResponseBody): T {
                    return if (request is DownloadRequest) {
                        val downloadConverter: DownloadConverter<T> = DownloadConverter<T>(request, callback as DownloadCallback)
                        downloadConverter.convert(body, callback.type)
                    } else {
                        GsonConverter<T>().convert(body, callback.type)
                    }
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
        RxHttpTagManager.Companion.getInstance().cancelTag(tag)
    }

    companion object {
        fun getInstance()= RxHttp.holder
    }

    init {
        val config: RxHttpConfig = RxHttpConfig.getInstance()
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
}