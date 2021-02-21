package com.jsy.tk.library.http

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