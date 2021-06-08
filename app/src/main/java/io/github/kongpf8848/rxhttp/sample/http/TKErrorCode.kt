package io.github.kongpf8848.rxhttp.sample.http

import com.google.gson.JsonSyntaxException
import com.jsy.tk.library.http.exception.ServerException
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException
import javax.net.ssl.SSLHandshakeException

object TKErrorCode {

    /**
     * 未知异常
     */
    const val ERRCODE_UNKNOWN = -1

    const val ERRCODE_UNKNOWN_DESC="unknown exception"

    /**
     * 网络异常
     */
    const val ERRCODE_NETWORK = -2

    const val ERRCODE_NETWORK_DESC="network exception"

    /**
     * 返回数据为null
     */
    const val ERRCODE_RESPONSE_NULL = -3

    const val ERRCODE_RESPONSE_NULL_DESC="response data is null"


    /**
     * Json转换异常
     */
    const val ERRCODE_JSON_PARSE = -4

    const val ERRCODE_JSON_PARSE_DESC="response data json parse error"


    fun handleThrowable(throwable: Throwable?): Pair<Int, String?> {
        return when (throwable) {
            /**
             * 网络异常
             */
            is ConnectException,
            is SocketTimeoutException,
            is UnknownHostException,
            is SSLHandshakeException
            -> {
                Pair(ERRCODE_NETWORK, throwable.message)
            }
            /**
             * Json异常
             */
            is JsonSyntaxException,
            is JSONException,
            is ParseException
            -> {
                Pair(ERRCODE_JSON_PARSE, throwable.message)
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
                Pair(ERRCODE_UNKNOWN, throwable?.message?: ERRCODE_UNKNOWN_DESC)
            }

        }
    }

}