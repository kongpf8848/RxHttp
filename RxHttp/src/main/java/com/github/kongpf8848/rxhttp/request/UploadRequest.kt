package com.github.kongpf8848.rxhttp.request

import android.app.Activity
import android.content.Context
import android.net.Uri
import androidx.fragment.app.Fragment
import com.github.kongpf8848.rxhttp.ByteArrayRequestBody
import com.github.kongpf8848.rxhttp.HttpConstants
import com.github.kongpf8848.rxhttp.Platform
import com.github.kongpf8848.rxhttp.ProgressRequestBody
import com.github.kongpf8848.rxhttp.callback.ProgressCallback
import com.github.kongpf8848.rxhttp.util.StreamUtil
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException

class UploadRequest<T> : PostRequest<T> {

    constructor(context: Context) : super(context)
    constructor(activity: Activity) : super(activity)
    constructor(fragment: Fragment) : super(fragment)

    override fun buildRequestBody(): RequestBody? {
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        val params: Map<String, Any?>? = getParams()
        if (!params.isNullOrEmpty()) {
            for((key,value) in params){
                if (value is File) {
                    builder.addFormDataPart(key, value.name, RequestBody.create(MediaType.parse(HttpConstants.MIME_TYPE_BINARY), value))
                } else if (value is Uri) {
                    try {
                        val inputStream = actualContext!!.contentResolver.openInputStream(value)
                        if (inputStream != null) {
                            val bytes = StreamUtil.toByte(inputStream)
                            builder.addFormDataPart(key, key, ByteArrayRequestBody(MediaType.parse(HttpConstants.MIME_TYPE_BINARY), bytes))
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    builder.addFormDataPart(key, value.toString())
                }
            }
        }
        return ProgressRequestBody(builder.build(), object : ProgressCallback {
            override fun onProgress(readBytes: Long, totalBytes: Long) {
                Platform.get().defaultCallbackExecutor()!!.execute(Runnable { callback!!.onProgress(readBytes, totalBytes) })
            }
        })
    }
}