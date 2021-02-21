package com.github.kongpf8848.rxhttp.sample.mvc

import com.github.kongpf8848.rxhttp.typebuilder.TypeBuilder
import com.github.kongpf8848.rxhttp.util.TypeUtil
import com.jsy.tk.library.http.TKResponse
import java.lang.reflect.Type

abstract class MVCHttpCallback<T> {

    private val type: Type

    init {
        val arg = TypeUtil.getType(javaClass)
        type = TypeBuilder
                .newInstance(TKResponse::class.java)
                .addTypeParam(arg)
                .build()
    }

    fun getType(): Type {
        return this.type
    }

    abstract fun onSuccess(result: T?)
    abstract fun onFailure(code: Int, msg: String?)
    open fun onStart() {}
    open fun onProgress(readBytes: Long, totalBytes: Long) {}
    open fun onComplete() {}

}