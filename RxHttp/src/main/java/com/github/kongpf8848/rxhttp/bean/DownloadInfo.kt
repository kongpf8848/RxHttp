package com.github.kongpf8848.rxhttp.bean

class DownloadInfo(var url: String, var destDir: String, var fileName: String)  {
    var total: Long = 0
    var progress: Long = 0

    override fun toString(): String {
        return "DownloadInfo{" +
                "total='" + total + '\'' +
                ", progress='" + progress + '\'' +
                '}'
    }

}