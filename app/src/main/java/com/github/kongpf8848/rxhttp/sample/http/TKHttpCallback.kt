package com.jsy.tk.library.http

import com.github.kongpf8848.rxhttp.util.TypeUtil
import ikidou.reflect.TypeBuilder
import java.lang.reflect.Type

abstract class TKHttpCallback<T>() {

    companion object{
        fun defaultType():Type{
            return TypeBuilder
                    .newInstance(TKResponse::class.java)
                    .addTypeParam(TKEmpty::class.java)
                    .build()
        }
    }
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