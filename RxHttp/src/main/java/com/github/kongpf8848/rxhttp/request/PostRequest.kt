package com.github.kongpf8848.rxhttp.request

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import androidx.fragment.app.Fragment
import com.github.kongpf8848.rxhttp.HttpConstants
import com.github.kongpf8848.rxhttp.util.GsonUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

open class PostRequest: AbsRequest {

    private var type: String? = null

    constructor(context: Context) : super(context)
    constructor(activity: Activity) : super(activity)
    constructor(fragment: Fragment) : super(fragment)

    fun type(type: String): PostRequest {
        this.type = type
        return this
    }

    public override fun buildRequestBody(): RequestBody? {
        val content = GsonUtil.toJson(getParams())
        return if (TextUtils.isEmpty(type)) {
            content.toRequestBody(HttpConstants.MIME_TYPE_JSON.toMediaTypeOrNull())
        } else {
            content.toRequestBody(type!!.toMediaTypeOrNull())
        }
    }
}