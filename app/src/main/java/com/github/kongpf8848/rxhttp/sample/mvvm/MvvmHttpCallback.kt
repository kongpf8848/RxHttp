package com.github.kongpf8848.rxhttp.sample.mvvm

import com.github.kongpf8848.rxhttp.sample.http.TKResponse
import com.github.kongpf8848.rxhttp.typebuilder.TypeBuilder
import com.github.kongpf8848.rxhttp.util.TypeUtil
import java.lang.reflect.Type

abstract class MvvmHttpCallback<T>() {

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

}