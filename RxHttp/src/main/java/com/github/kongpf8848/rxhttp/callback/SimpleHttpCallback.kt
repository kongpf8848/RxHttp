package com.github.kongpf8848.rxhttp.callback

import java.lang.reflect.Type

class SimpleHttpCallback<T> : HttpCallback<T> {
    constructor() : super()
    constructor(type: Type) : super(type)

    override fun onStart() {}
    override fun onNext(response: T?) {}
    override fun onError(e: Throwable?) {}
    override fun onComplete() {}
    override fun onProgress(readBytes: Long, totalBytes: Long) {}
}