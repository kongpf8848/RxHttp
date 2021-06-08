package io.github.kongpf8848.rxhttp.sample.mvvm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import io.github.kongpf8848.rxhttp.RxHttp
import io.github.kongpf8848.rxhttp.callback.DownloadCallback
import io.github.kongpf8848.rxhttp.callback.HttpCallback
import io.github.kongpf8848.rxhttp.sample.http.TKResponse

/**
 * MVVM架构网络仓库
 * UI->ViewModel->Repository->LiveData(ViewModel)->UI
 */
class NetworkRepository private constructor() {

    companion object {
        val instance = NetworkRepository.holder
    }

    private object NetworkRepository {
        val holder = NetworkRepository()
    }

    inline fun <reified T> wrapHttpCallback(): MvvmHttpCallback<T> {
        return object : MvvmHttpCallback<T>() {

        }
    }

    inline fun <reified T> newCallback(liveData: MutableLiveData<TKState<T>>): HttpCallback<TKResponse<T>> {
        val type = wrapHttpCallback<T>().getType()
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

                /**
                 * 亲,此处不要做任何操作,不要给LiveData赋值,防止onNext对应的LiveData数据被覆盖，
                 * 在TKState类handle方法里会特别处理回调的，放心好了
                 */
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
        RxHttp.getInstance().post(context)
                .url(url)
                .params(params)
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
        RxHttp.getInstance().postForm(context)
                .url(url)
                .params(params)
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
        RxHttp.getInstance().put(context)
                .url(url)
                .params(params)
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
        RxHttp.getInstance().delete(context)
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
    val map =Map<String,Any>()
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
        RxHttp.getInstance().upload(context)
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
     * md5:下载文件的MD5值
     * breakpoint:是否支持断点下载,默认为true
     */
    fun httpDownload(context: Context, url: String, dir: String, filename: String, callback: DownloadCallback, md5: String? = null, breakPoint: Boolean = true, tag: Any? = null) {
        RxHttp.getInstance().download(context).dir(dir).filename(filename).breakpoint(breakPoint).md5(md5).url(url).tag(tag).enqueue(callback)
    }

}