package com.github.kongpf8848.rxhttp.request

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import androidx.fragment.app.Fragment
import com.github.kongpf8848.rxhttp.HttpConstants
import com.github.kongpf8848.rxhttp.util.GsonUtil
import okhttp3.MediaType
import okhttp3.RequestBody

open class PostRequest<T> : AbsRequest<T> {

    private var type: String? = null

    constructor(context: Context) : super(context)
    constructor(activity: Activity) : super(activity)
    constructor(fragment: Fragment) : super(fragment)

    fun type(type: String): PostRequest<T> {
        this.type = type
        return this
    }

    public override fun buildRequestBody(): RequestBody? {
        val content = GsonUtil.toJson(getParams())
        return if (TextUtils.isEmpty(type)) {
            RequestBody.create(MediaType.parse(HttpConstants.MIME_TYPE_JSON), content)
        } else {
            RequestBody.create(MediaType.parse(type!!), content)
        }
    }
}