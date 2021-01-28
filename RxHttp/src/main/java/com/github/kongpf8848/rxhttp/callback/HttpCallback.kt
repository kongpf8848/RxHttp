package com.github.kongpf8848.rxhttp.callback

import com.github.kongpf8848.rxhttp.util.TypeUtil
import java.lang.reflect.Type

abstract class HttpCallback<T> : BaseCallback<T> {
    var type: Type

    constructor() {
        type = TypeUtil.getType(javaClass)
    }

    constructor(type: Type) {
        this.type = type
    }

    override fun onStart() {
    }

    override fun onComplete() {
    }

    override fun onProgress(readBytes: Long, totalBytes: Long) {

    }
}