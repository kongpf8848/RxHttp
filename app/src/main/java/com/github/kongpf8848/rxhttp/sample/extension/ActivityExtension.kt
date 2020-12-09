package com.github.kongpf8848.rxhttp.sample.extension

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts


/**
 * 获取内容
 */
fun ComponentActivity.getContent(input: String, callback: ((uri: Uri) -> Unit)? = null) {
    registerForActivityResult(ActivityResultContracts.GetContent(), ActivityResultCallback {
        callback?.invoke(it)
    }).launch(input)
}