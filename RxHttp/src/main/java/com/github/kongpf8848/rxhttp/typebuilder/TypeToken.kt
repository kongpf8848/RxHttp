package com.github.kongpf8848.rxhttp.typebuilder

import com.github.kongpf8848.rxhttp.typebuilder.exception.TypeException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class TypeToken<T> {

    val type: Type

    init {
        val superclass = javaClass.genericSuperclass
        if (superclass is Class<*>) {
            throw TypeException("No generics found!")
        }
        val type = superclass as ParameterizedType
        this.type = type.actualTypeArguments[0]
    }
}