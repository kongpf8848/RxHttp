package com.github.kongpf8848.rxhttp.converter

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
        response = if (type === String::class.java) {
            result as T
        } else {
            try {
                Gson().fromJson(result, type) as T
            } catch (e: JsonSyntaxException) {
                e.printStackTrace()
                throw e
            }
        }
        return response
    }
}