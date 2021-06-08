package io.github.kongpf8848.rxhttp.sample.utils

import android.text.TextUtils
import com.google.gson.Gson

object GsonUtils {

    private val gson = Gson()

    fun toJson(obj: Any?): String {
        var gsonString = ""
        if (obj != null && !TextUtils.isEmpty(obj.toString())) {
            gsonString = gson.toJson(obj)
        }
        return gsonString
    }
}