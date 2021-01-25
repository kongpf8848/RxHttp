package com.github.kongpf8848.rxhttp.sample.mvc

import android.content.Context
import com.github.kongpf8848.rxhttp.RxHttp
import com.github.kongpf8848.rxhttp.callback.SimpleHttpCallback
import com.github.kongpf8848.rxhttp.request.*
import com.github.kongpf8848.rxhttp.sample.utils.LogUtils
import com.google.gson.JsonSyntaxException
import com.jsy.tk.library.http.TKErrorCode
import com.jsy.tk.library.http.TKResponse
import com.jsy.tk.library.http.exception.ServerException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException
import javax.net.ssl.SSLHandshakeException

object TKApi {

    /**
     * GET请求
     */
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



    inline fun <reified T> simpleHttpCallback(callback: MVCHttpCallback<T>): SimpleHttpCallback<TKResponse<T>> {
        return object : SimpleHttpCallback<TKResponse<T>>(callback.getType()) {
            override fun onStart() {
                super.onStart()
                callback.onStart()
            }

            override fun onNext(response: TKResponse<T>?) {
                super.onNext(response)
                if (response != null) {
                    if (response.isSuccess()) {
                        callback.onSuccess(response.data)
                    } else {
                        return onError(ServerException(response.code, response.msg))
                    }

                } else {
                    return onError(ServerException(TKErrorCode.ERRCODE_RESPONSE_NULL, null))
                }

            }

            override fun onError(e: Throwable?) {
                super.onError(e)
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


    fun handleThrowable(throwable: Throwable?): Pair<Int, String?> {
        LogUtils.d("handleThrowable:${throwable}")
        return when (throwable) {
            /**
             * 网络异常
             */
            is ConnectException,
            is SocketTimeoutException,
            is UnknownHostException,
            is SSLHandshakeException
            -> {
                Pair(TKErrorCode.ERRCODE_NETWORK, throwable.message)
            }
            /**
             * Json异常
             */
            is JsonSyntaxException,
            is JSONException,
            is ParseException
            -> {
                Pair(TKErrorCode.ERRCODE_JSON_PARSE, throwable.message)
            }
            /**
             * Retrofit返回的异常
             */
            is HttpException -> {
                Pair(throwable.code(), throwable.message)
            }

            /**
             * 服务端返回的异常
             */
            is ServerException -> {
                Pair(throwable.code, throwable.msg)
            }


            /**
             * 未知异常
             */
            else -> {
                Pair(TKErrorCode.ERRCODE_UNKNOWN, "Unknown Exception")
            }

        }
    }

}