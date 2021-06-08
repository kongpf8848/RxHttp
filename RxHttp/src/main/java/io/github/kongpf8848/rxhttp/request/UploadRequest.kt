package io.github.kongpf8848.rxhttp.request

import android.app.Activity
import android.content.Context
import android.net.Uri
import androidx.fragment.app.Fragment
import io.github.kongpf8848.rxhttp.ByteArrayRequestBody
import io.github.kongpf8848.rxhttp.HttpConstants
import io.github.kongpf8848.rxhttp.Platform
import io.github.kongpf8848.rxhttp.ProgressRequestBody
import io.github.kongpf8848.rxhttp.callback.ProgressCallback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException

class UploadRequest : PostRequest {

    constructor(context: Context) : super(context)
    constructor(activity: Activity) : super(activity)
    constructor(fragment: Fragment) : super(fragment)

    override fun buildRequestBody(): RequestBody? {
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        val params: Map<String, Any?>? = getParams()
        if (!params.isNullOrEmpty()) {
            for((key,value) in params){
                if (value is File) {
                    builder.addFormDataPart(key, value.name, RequestBody.create(HttpConstants.MIME_TYPE_BINARY.toMediaTypeOrNull(), value))
                } else if (value is Uri) {
                    try {
                        val inputStream = actualContext!!.contentResolver.openInputStream(value)
                        if (inputStream != null) {
                            val bytes = inputStream.readBytes()
                            inputStream.close()
                            builder.addFormDataPart(key, key, ByteArrayRequestBody(HttpConstants.MIME_TYPE_BINARY.toMediaTypeOrNull(), bytes))
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
                Platform.get().defaultCallbackExecutor()?.execute { callback?.onProgress(readBytes, totalBytes) }
            }
        })
    }
}