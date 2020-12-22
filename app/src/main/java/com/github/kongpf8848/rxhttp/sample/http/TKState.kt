package com.github.kongpf8848.rxhttp.sample.http

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

class TKState<T> {

    var state: Int
    var code = TKErrorCode.ERRCODE_UNKNOWN
    var msg: String? = null
    var data: T? = null
    var error: Throwable? = null
    var progress: Long = 0
    var total: Long = 0

    @JvmOverloads
    constructor(state: Int, data: T? = null, msg: String? = "") {
        this.state = state
        this.data = data
        this.msg = msg
    }

    constructor(state: Int, throwable: Throwable?) {
        this.state = state
        handleThrowable(throwable).run {
            this@TKState.code = first
            this@TKState.msg = second
        }
    }

    constructor(state: Int, progress: Long, total: Long) {
        this.state = state
        this.progress = progress
        this.total = total
    }

    fun handle(callback: TKState.HandleCallback<T>.() -> Unit) {
        val handleCallback = HandleCallback<T>()
        handleCallback.apply(callback)
        when (state) {
            START ->
                handleCallback.onStart?.invoke()
            SUCCESS ->
                handleCallback.onSuccess?.invoke(data)
            FAIL -> {
                handleCallback.onFailure?.invoke(code, msg)
            }
            PROGRESS ->
                handleCallback.onProgress?.invoke(progress, total)
        }
        if(state== SUCCESS || state== FAIL){
            handleCallback.onComplete?.invoke()
        }
    }

    open class HandleCallback<T> {
        var onStart: (() -> Unit)? = null
        var onSuccess: ((T?) -> Unit)? = null
        var onFailure: ((Int, String?) -> Unit)? = null
        var onComplete: (() -> Unit)? = null
        var onProgress: ((Long, Long) -> Unit)? = null

        fun onStart(callback:(() -> Unit)?){
            this.onStart=callback
        }
        fun onSuccess(callback:((T?) -> Unit)?){
            this.onSuccess=callback
        }
        fun onFailure(callback:((Int, String?) -> Unit)?){
            this.onFailure=callback
        }
        fun onComplete(callback:(() -> Unit)?){
            this.onComplete=callback
        }

        fun onProgress(callback:((Long, Long) -> Unit)?){
            this.onProgress=callback
        }
    }

    companion object {
        const val START = 0
        const val SUCCESS = 1
        const val FAIL = 2
        const val PROGRESS = 3
        fun <T> start(): TKState<T> {
            return TKState(START)
        }

        fun <T> response(response: TKResponse<T>?): TKState<T> {
            if (response != null) {
                if (response.isSuccess()) {
                    return TKState(SUCCESS, response.data, null)
                } else {
                    return error(ServerException(response.code, response.msg))
                }

            } else {
                return error(ServerException(TKErrorCode.ERRCODE_RESPONSE_NULL, null))
            }

        }


        fun <T> error(t: Throwable?): TKState<T> {
            return TKState(FAIL, t)
        }

        fun <T> progress(progress: Long, total: Long): TKState<T> {
            return TKState(PROGRESS, progress, total)
        }
    }

    private fun handleThrowable(throwable: Throwable?): Pair<Int, String?> {
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
