package io.github.kongpf8848.rxhttp.sample.mvvm

import com.jsy.tk.library.http.exception.ServerException
import io.github.kongpf8848.rxhttp.sample.http.TKErrorCode
import io.github.kongpf8848.rxhttp.sample.http.TKErrorCode.handleThrowable
import io.github.kongpf8848.rxhttp.sample.http.TKResponse
import io.github.kongpf8848.rxhttp.sample.http.exception.NullResponseException

/**
 *将HttpCallback回调转化为对应的LiveData
 */
class TKState<T> {

    var state: Int = 0
    var code = TKErrorCode.ERRCODE_UNKNOWN
    var msg: String? = null
    var data: T? = null
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

    fun handle(handleCallback: HandleCallback<T>.() -> Unit) {
        val callback = HandleCallback<T>()
        callback.apply(handleCallback)
        when (state) {
            START -> {
                callback.onStart?.invoke()
            }
            SUCCESS -> {
                callback.onSuccess?.invoke(data)
            }
            FAIL -> {
                callback.onFailure?.invoke(code, msg)
            }
            PROGRESS -> {
                callback.onProgress?.invoke(progress, total)
            }
        }
        if (state == SUCCESS || state == FAIL) {
            callback.onComplete?.invoke()
        }
    }

    open class HandleCallback<T> {
        var onStart: (() -> Unit)? = null
        var onSuccess: ((T?) -> Unit)? = null
        var onFailure: ((Int, String?) -> Unit)? = null
        var onComplete: (() -> Unit)? = null
        var onProgress: ((Long, Long) -> Unit)? = null

        fun onStart(callback: (() -> Unit)?) {
            this.onStart = callback
        }

        fun onSuccess(callback: ((T?) -> Unit)?) {
            this.onSuccess = callback
        }

        fun onFailure(callback: ((Int, String?) -> Unit)?) {
            this.onFailure = callback
        }

        fun onComplete(callback: (() -> Unit)?) {
            this.onComplete = callback
        }

        fun onProgress(callback: ((Long, Long) -> Unit)?) {
            this.onProgress = callback
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
                return error(NullResponseException(TKErrorCode.ERRCODE_RESPONSE_NULL, TKErrorCode.ERRCODE_RESPONSE_NULL_DESC))
            }

        }

        fun <T> error(t: Throwable?): TKState<T> {
            return TKState(FAIL, t)
        }

        fun <T> progress(progress: Long, total: Long): TKState<T> {
            return TKState(PROGRESS, progress, total)
        }
    }

}
