package com.github.kongpf8848.rxhttp.request

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import okhttp3.RequestBody

class DownloadRequest : AbsRequest {
    var dir: String = ""
    var filename: String = ""
    var md5: String? = null
    var isBreakpoint = false

    constructor(context: Context) : super(context) {}
    constructor(activity: Activity) : super(activity) {}
    constructor(fragment: Fragment) : super(fragment) {}

    fun dir(dir: String): DownloadRequest {
        this.dir = dir
        return this
    }

    fun filename(filename: String): DownloadRequest {
        this.filename = filename
        return this
    }

    fun breakpoint(breakpoint: Boolean): DownloadRequest {
        isBreakpoint = breakpoint
        return this
    }

    fun md5(md5: String?): DownloadRequest {
        this.md5 = md5
        return this
    }

    override fun buildRequestBody(): RequestBody? {
        return null
    }
}