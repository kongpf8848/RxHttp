package com.github.kongpf8848.rxhttp.callback

interface ProgressCallback {
    fun onProgress(readBytes: Long, totalBytes: Long)
}