package com.github.kongpf8848.rxhttp.bean

class DownloadInfo(var url: String, var dir: String, var fileName: String,var total: Long = 0, var progress: Long = 0)  {

    override fun toString(): String {
        return "DownloadInfo(url='$url', dir='$dir', fileName='$fileName', total=$total, progress=$progress)"
    }

}