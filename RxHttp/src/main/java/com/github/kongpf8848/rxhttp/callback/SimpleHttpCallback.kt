package com.github.kongpf8848.rxhttp.callback

import java.lang.reflect.Type

open class SimpleHttpCallback<T>(type: Type) : HttpCallback<T>(type) {
    override fun onStart() {}
    override fun onNext(response: T?) {}
    override fun onError(e: Throwable?) {}
    override fun onComplete() {}
    override fun onProgress(readBytes: Long, totalBytes: Long) {}
}