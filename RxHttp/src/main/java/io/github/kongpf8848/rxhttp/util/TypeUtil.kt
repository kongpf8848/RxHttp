package io.github.kongpf8848.rxhttp.util

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

object TypeUtil {

    fun getType(subclass: Class<*>): Type {
        val superclass = subclass.genericSuperclass
        return if (superclass is Class<*>) {
            subclass
        } else {
            val parameterized = superclass as ParameterizedType
            parameterized.actualTypeArguments[0]
        }
    }
}