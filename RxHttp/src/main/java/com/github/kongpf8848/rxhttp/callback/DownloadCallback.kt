package com.github.kongpf8848.rxhttp.callback

import com.github.kongpf8848.rxhttp.bean.DownloadInfo

abstract class DownloadCallback : HttpCallback<DownloadInfo>() {
    abstract fun onProgress(downloadInfo: DownloadInfo?)
}