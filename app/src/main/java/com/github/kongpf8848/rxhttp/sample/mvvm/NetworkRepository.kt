package com.github.kongpf8848.rxhttp.sample.mvvm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.github.kongpf8848.rxhttp.RxHttp
import com.github.kongpf8848.rxhttp.callback.DownloadCallback
import com.github.kongpf8848.rxhttp.callback.HttpCallback
import com.github.kongpf8848.rxhttp.sample.http.TKState
import com.github.kongpf8848.rxhttp.sample.utils.GsonUtils
import com.jsy.tk.library.http.TKHttpCallback
import com.jsy.tk.library.http.TKResponse


class NetworkRepository private constructor(){

    companion object {
        val instance = NetworkRepository.holder
    }

    private object NetworkRepository {
        val holder = NetworkRepository()
    }

    inline fun <reified T> wrapHttpCallback(): TKHttpCallback<T> {
        return object:TKHttpCallback<T>(){

        }
    }

    inline fun <reified T> newCallback(liveData: MutableLiveData<TKState<T>>): HttpCallback<TKResponse<T>> {
        val type= wrapHttpCallback<T>().getType()
        return object : HttpCallback<TKResponse<T>>(type) {
            override fun onStart() {
                liveData.value = TKState.start()
            }

            override fun onNext(response: TKResponse<T>?) {
                liveData.value = TKState.response(response)
            }

            override fun onError(e: Throwable?) {
                liveData.value = TKState.error(e)
            }

            override fun onComplete() {
                liveData.value = TKState.complete()
            }

            override fun onProgress(readBytes: Long, totalBytes: Long) {
                liveData.value = TKState.progress(readBytes, totalBytes)
            }
        }
    }

    inline fun <reified T> httpGet(
        context: Context,
        url: String,
        params: Map<String, Any?>?,
        tag: Any? = null
    ): MutableLiveData<TKState<T>> {
        val liveData = MutableLiveData<TKState<T>>()
        RxHttp.getInstance()
            .get(context)
            .url(url)
            .params(params)
            .tag(tag)
            .enqueue(newCallback(liveData))
        return liveData
    }


    inline fun <reified T> httpPost(
        context: Context,
        url: String,
        params: Map<String, Any?>?,
        tag: Any? = null
    ): MutableLiveData<TKState<T>> {
        val liveData = MutableLiveData<TKState<T>>()
        val json = GsonUtils.toJson(params)
        RxHttp.getInstance()
            .post(context)
            .content(json)
            .url(url)
            .tag(tag)
            .enqueue(newCallback(liveData))
        return liveData
    }

    inline fun <reified T> httpPostForm(
            context: Context,
            url: String,
            params: Map<String, Any?>?,
            tag: Any? = null
    ): MutableLiveData<TKState<T>> {
        val liveData = MutableLiveData<TKState<T>>()
        RxHttp.getInstance()
                .postForm(context)
                .params(params)
                .url(url)
                .tag(tag)
                .enqueue(newCallback(liveData))
        return liveData
    }

    inline fun <reified T> httpPut(
        context: Context,
        url: String,
        params: Map<String, Any?>?,
        tag: Any? = null
    ): MutableLiveData<TKState<T>> {
        val liveData = MutableLiveData<TKState<T>>()
        val json = GsonUtils.toJson(params)
        RxHttp.getInstance()
            .put(context)
            .content(json)
            .url(url)
            .tag(tag)
            .enqueue(newCallback(liveData))
        return liveData
    }

    inline fun <reified T> httpDelete(
        context: Context,
        url: String,
        params: Map<String, Any?>?,
        tag: Any? = null
    ): MutableLiveData<TKState<T>> {
        val liveData = MutableLiveData<TKState<T>>()
        RxHttp.getInstance()
            .delete(context)
            .params(params)
            .url(url)
            .tag(tag)
            .enqueue(newCallback(liveData))
        return liveData
    }


    /**
     *上传
     *支持上传多个文件,map中对应的value类型为File类型或Uri类型
     *支持监听上传进度
    val map =HashMap<String,Any>()
    map.put("model", "xiaomi")
    map.put("os", "android")
    map.put("avatar",File("xxx"))
    map.put("video",uri)
     */
    inline fun <reified T> httpUpload(
        context: Context,
        url: String,
        params: Map<String, Any?>?,
        tag: Any? = null
    ): MutableLiveData<TKState<T>> {
        val liveData = MutableLiveData<TKState<T>>()
        RxHttp.getInstance()
            .upload(context)
            .url(url)
            .params(params)
            .tag(tag)
            .enqueue(newCallback(liveData))
        return liveData
    }


    /**
     * 下载
     * context:上下文,如不需要和生命周期绑定,应该传递applicationContext
     * url:下载地址
     * dir:本地目录路径
     * filename:保存文件名称
     * callback:下载进度回调
     * breakpoint:是否支持断点下载,默认为true
     */
    fun httpDownload(context: Context, url: String, dir:String, fileName:String, callback: DownloadCallback, breakPoint:Boolean=true, tag: Any? = null){
        RxHttp.getInstance().download(context)
            .dir(dir)
            .filename(fileName)
            .breakpoint(breakPoint)
            .url(url)
            .tag(tag)
            .enqueue(callback)
    }

}