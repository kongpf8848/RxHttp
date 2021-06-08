package io.github.kongpf8848.rxhttp.sample.mvc

import android.content.Context
import com.jsy.tk.library.http.exception.ServerException
import io.github.kongpf8848.rxhttp.RxHttp
import io.github.kongpf8848.rxhttp.callback.HttpCallback
import io.github.kongpf8848.rxhttp.sample.http.TKErrorCode
import io.github.kongpf8848.rxhttp.sample.http.TKErrorCode.handleThrowable
import io.github.kongpf8848.rxhttp.sample.http.TKResponse
import io.github.kongpf8848.rxhttp.sample.http.exception.NullResponseException

object MVCApi {

    /**
     * GET请求
     * context:上下文
     * url：请求url
     * params:参数列表，可为null
     * tag：标识一个网络请求
     * callback：网络请求回调
     */
    inline fun <reified T> httpGet(
            context: Context,
            url: String,
            params: Map<String, Any?>?,
            tag: Any? = null,
            callback: MVCHttpCallback<T>
    ) {
        RxHttp.getInstance().get(context)
                .url(url)
                .params(params)
                .tag(tag)
                .enqueue(simpleHttpCallback(callback))
    }

    /**
     * POST请求
     * context:上下文
     * url：请求url
     * params:参数列表，可为null
     * tag：标识一个网络请求
     * callback：网络请求回调
     */
    inline fun <reified T> httpPost(
            context: Context,
            url: String,
            params: Map<String, Any?>?,
            tag: Any? = null,
            callback: MVCHttpCallback<T>
    ) {
        RxHttp.getInstance().post(context)
                .url(url)
                .params(params)
                .tag(tag)
                .enqueue(simpleHttpCallback(callback))
    }

    inline fun <reified T> httpPostForm(
            context: Context,
            url: String,
            params: Map<String, Any?>?,
            tag: Any? = null,
            callback: MVCHttpCallback<T>
    ) {

        RxHttp.getInstance().postForm(context)
                .url(url)
                .params(params)
                .tag(tag)
                .enqueue(simpleHttpCallback(callback))
    }

    inline fun <reified T> httpPut(
            context: Context,
            url: String,
            params: Map<String, Any?>?,
            tag: Any? = null,
            callback: MVCHttpCallback<T>
    ) {

        RxHttp.getInstance().put(context)
                .url(url)
                .params(params)
                .tag(tag)
                .enqueue(simpleHttpCallback(callback))
    }

    inline fun <reified T> httpDelete(
            context: Context,
            url: String,
            params: Map<String, Any?>?,
            tag: Any? = null,
            callback: MVCHttpCallback<T>
    ) {
        RxHttp.getInstance().delete(context)
                .url(url)
                .params(params)
                .tag(tag)
                .enqueue(simpleHttpCallback(callback))
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
            tag: Any? = null,
            callback: MVCHttpCallback<T>
    ) {
        RxHttp.getInstance().upload(context)
                .url(url)
                .params(params)
                .tag(tag)
                .enqueue(simpleHttpCallback(callback))

    }


    inline fun <reified T> simpleHttpCallback(callback: MVCHttpCallback<T>): HttpCallback<TKResponse<T>> {
        return object : HttpCallback<TKResponse<T>>(callback.getType()) {
            override fun onStart() {
                super.onStart()
                callback.onStart()
            }

            override fun onNext(response: TKResponse<T>?) {
                if (response != null) {
                    if (response.isSuccess()) {
                        callback.onSuccess(response.data)
                    } else {
                        return onError(ServerException(response.code, response.msg))
                    }

                } else {
                    return onError(NullResponseException(TKErrorCode.ERRCODE_RESPONSE_NULL, TKErrorCode.ERRCODE_RESPONSE_NULL_DESC))
                }

            }

            override fun onError(e: Throwable?) {
                handleThrowable(e).run {
                    callback.onFailure(first, second)
                }
            }

            override fun onComplete() {
                super.onComplete()
                callback.onComplete()
            }

            override fun onProgress(readBytes: Long, totalBytes: Long) {
                super.onProgress(readBytes, totalBytes)
                callback.onProgress(readBytes, totalBytes)
            }
        }
    }


}