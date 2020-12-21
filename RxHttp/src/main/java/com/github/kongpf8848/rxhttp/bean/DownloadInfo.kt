package com.github.kongpf8848.rxhttp.bean

class DownloadInfo(url: String?, var destDir: String?, var fileName: String?) : ProgressInfo(url) {

    override fun toString(): String {
        return "DownloadInfo{" +
                "total='" + total + '\'' +
                ", progress='" + progress + '\'' +
                '}'
    }

}