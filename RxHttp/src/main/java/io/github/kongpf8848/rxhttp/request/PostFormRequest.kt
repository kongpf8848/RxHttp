package io.github.kongpf8848.rxhttp.request

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import okhttp3.RequestBody

class PostFormRequest : AbsRequest {
    constructor(context: Context) : super(context)
    constructor(activity: Activity) : super(activity)
    constructor(fragment: Fragment) : super(fragment)

    public override fun buildRequestBody(): RequestBody? {
        return null
    }
}