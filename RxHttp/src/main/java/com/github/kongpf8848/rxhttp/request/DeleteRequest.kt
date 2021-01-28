package com.github.kongpf8848.rxhttp.request

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import okhttp3.RequestBody

class DeleteRequest : AbsRequest {
    constructor(context: Context) : super(context)
    constructor(fragment: Fragment) : super(fragment)
    constructor(activity: Activity) : super(activity)

    override fun buildRequestBody(): RequestBody? {
        return null
    }
}