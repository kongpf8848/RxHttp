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

        if (type === String::class.java) {
            return body.string() as T
        }
        else if (type === Boolean::class.java || type === Boolean::class.javaPrimitiveType) {
            response= body.string().toBoolean() as T
        }
        else if (type === Byte::class.java || type === Byte::class.javaPrimitiveType) {
            response= body.string().toByte() as T
        }
        else if (type === Char::class.java || type === Char::class.javaPrimitiveType) {
            response= body.string()[0] as T
        }
        else if (type === Double::class.java || type === Double::class.javaPrimitiveType) {
            response= body.string().toDouble() as T
        }
        else if (type === Float::class.java || type === Float::class.javaPrimitiveType) {
            response= body.string().toFloat() as T
        }
        else if (type === Int::class.java || type === Int::class.javaPrimitiveType) {
            response= body.string().toInt() as T
        }
        if (type === Long::class.java || type === Long::class.javaPrimitiveType) {
            response= body.string().toLong() as T
        }
        if (type === Short::class.java || type === Short::class.javaPrimitiveType) {
            response= body.string().toShort() as T
        }
        else {
            try {
               response= Gson().fromJson(result, type) as T
            } catch (e: JsonSyntaxException) {
                e.printStackTrace()
                throw e
            }
        }
        return response
    }
}