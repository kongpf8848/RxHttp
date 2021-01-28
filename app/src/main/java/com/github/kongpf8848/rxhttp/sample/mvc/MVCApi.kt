package com.github.kongpf8848.rxhttp.sample.mvc

import android.content.Context
import com.github.kongpf8848.rxhttp.RxHttp
import com.github.kongpf8848.rxhttp.callback.HttpCallback
import com.github.kongpf8848.rxhttp.request.*
import com.github.kongpf8848.rxhttp.sample.http.TKErrorCode
import com.github.kongpf8848.rxhttp.sample.http.TKErrorCode.handleThrowable
import com.github.kongpf8848.rxhttp.sample.http.exception.NullResponseException
import com.jsy.tk.library.http.TKResponse
import com.jsy.tk.library.http.exception.ServerException

object MVCApi {

    inline fun <reified T> httpGet(
            context: Context,
            url: String,
            params: Map<String, Any?>?,
            tag: Any? = null,
            callback: MVCHttpCallback<T>
    ) {
        val request: GetRequest<TKResponse<T>> = RxHttp.getInstance().get(context)
        request.run {
            url(url)
            params(params)
            tag(tag)
            enqueue(simpleHttpCallback(callback))
        }
    }

    inline fun <reified T> httpPost(
            context: Context,
            url: String,
            params: Map<String, Any?>?,
            tag: Any? = null,
            callback: MVCHttpCallback<T>
    ) {
        val request: PostRequest<TKResponse<T>> = RxHttp.getInstance().post(context)
        request.run {
            url(url)
            params(params)
            tag(tag)
            enqueue(simpleHttpCallback(callback))
        }
    }

    inline fun <reified T> httpPostForm(
            context: Context,
            url: String,
            params: Map<String, Any?>?,
            tag: Any? = null,
            callback: MVCHttpCallback<T>
    ) {

        val request: PostFormRequest<TKResponse<T>> = RxHttp.getInstance().postForm(context)
        request.run {
            url(url)
            params(params)
            tag(tag)
            enqueue(simpleHttpCallback(callback))
        }
    }

    inline fun <reified T> httpPut(
            context: Context,
            url: String,
            params: Map<String, Any?>?,
            tag: Any? = null,
            callback: MVCHttpCallback<T>
    ){

        val request: PutRequest<TKResponse<T>> = RxHttp.getInstance().put(context)
        request.run {
            params(params)
            url(url)
            tag(tag)
            enqueue(simpleHttpCallback(callback))
        }
    }

    inline fun <reified T> httpDelete(
            context: Context,
            url: String,
            params: Map<String, Any?>?,
            tag: Any? = null,
            callback: MVCHttpCallback<T>
    ){
        val request: DeleteRequest<TKResponse<T>> = RxHttp.getInstance().delete(context)
        request.run {
            params(params)
            url(url)
            tag(tag)
            enqueue(simpleHttpCallback(callback))
        }
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
    ){
        val request: UploadRequest<TKResponse<T>> = RxHttp.getInstance().upload(context)
        request.run {
            url(url)
            params(params)
            tag(tag)
            enqueue(simpleHttpCallback(callback))
        }
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
                    return onError(NullResponseException(TKErrorCode.ERRCODE_RESPONSE_NULL,TKErrorCode.ERRCODE_RESPONSE_NULL_DESC))
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
                callback.onComplete()
            }
        }
    }



}