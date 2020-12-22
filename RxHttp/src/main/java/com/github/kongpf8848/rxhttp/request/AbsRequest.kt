package com.github.kongpf8848.rxhttp.request

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.github.kongpf8848.rxhttp.RxHttp
import com.github.kongpf8848.rxhttp.RxHttpTagManager
import com.github.kongpf8848.rxhttp.callback.HttpCallback
import okhttp3.RequestBody

abstract class AbsRequest<T> {
     var context:Any?=null
     var url: String? = null
    private var ta: Any? = null
    private var headers: HashMap<String, String> = HashMap()
    private var param: Map<String, Any?>?=null
    protected var callback: HttpCallback<T>? = null

    protected abstract fun buildRequestBody(): RequestBody?

    internal constructor(context: Context) {
        this.context = context
    }

    internal constructor(fragment: Fragment) {
        context = fragment
    }

    internal constructor(activity: Activity) {
        context = activity
    }

    fun url(url: String): AbsRequest<T> {
        this.url = url
        return this
    }

    fun tag(tag: Any?): AbsRequest<T> {
        this.ta = tag
        return this
    }

    fun header(key: String, value: String): AbsRequest<T> {
        headers[key] = value
        return this
    }

    fun headers(headers: HashMap<String, String>?): AbsRequest<T> {
        if (headers != null) {
            this.headers = headers
        }
        return this
    }


    fun params(params: Map<String, Any?>?): AbsRequest<T> {
        if (params != null) {
            this.param = params
        }
        return this
    }

    val actualContext: Context?
        get() {
            if (context is Context) {
                return context as Context
            } else if (context is Fragment) {
                return (context as Fragment).context
            }
            return null
        }

    fun getTag(): Any? {
        return if (ta == null) {
            RxHttpTagManager.generateRandomTag()
        } else ta
    }

    fun getHeaders(): Map<String, String> {
        return headers
    }

    fun getParams(): Map<String, Any?>? {
        if (param != null && param!!.containsValue(null)) {
            val newMap: MutableMap<String, Any?> = HashMap()
            val set: Set<String> = param!!.keys
            for (key in set) {
                val value = param!![key]
                if (value != null) {
                    newMap[key] = value
                }
            }
            return newMap
        }
        return param
    }

    inline fun enqueue(callback: HttpCallback<T>) {
        this.callback = callback
        RxHttp.getInstance().enqueue(this, callback)
    }
}