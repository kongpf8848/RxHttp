package com.github.kongpf8848.rxhttp.callback

internal interface BaseCallback<T> {
    /**
     * http请求开始时回调
     */
    fun onStart()

    /**
     * http请求成功时回调
     */
    fun onNext(response: T?)

    /**
     * http请求失败时回调
     */
    fun onError(e: Throwable?)

    /**
     * http请求完成时回调
     */
    fun onComplete()

    /**
     * 上传下载进度回调
     * @param readBytes
     * @param totalBytes
     */
    fun onProgress(readBytes: Long, totalBytes: Long)
}