package com.github.kongpf8848.rxhttp.callback

import com.github.kongpf8848.rxhttp.util.TypeUtil
import java.lang.reflect.Type

abstract class HttpCallback<T> : BaseCallback<T> {
    lateinit var type: Type


    constructor() {
        type = TypeUtil.getType(javaClass)
    }

    constructor(type: Type) {
        this.type = type
    }

}