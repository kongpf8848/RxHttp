package com.github.kongpf8848.rxhttp.converter

import android.text.TextUtils
import com.github.kongpf8848.rxhttp.exception.RxHttpException
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.ResponseBody
import java.lang.reflect.Type

class GsonConverter<T> : IConverter<T> {
    @Throws(Exception::class)
    override fun convert(body: ResponseBody, type: Type): T {
        var response: T? = null
        val result = body.string()
        body.close()
        if (TextUtils.isEmpty(result)) {
            throw RxHttpException(RxHttpException.Companion.RXHTTP_CODE_RESPONSE_DATA_EMPTY_EXCEPTION, "response data is empty")
        }
        response = if (type === String::class.java) {
            result as T
        } else {
            try {
                Gson().fromJson(result, type) as T
            } catch (e: JsonSyntaxException) {
                e.printStackTrace()
                throw RxHttpException(RxHttpException.Companion.RXHTTP_CODE_JSON_PARSE_EXCEPTION, e.message)
            }
        }
        if (response == null) {
            throw RxHttpException(RxHttpException.Companion.RXHTTP_CODE_RESPONSE_NULL_EXCEPTION, "response bean is null")
        }
        return response
    }
}