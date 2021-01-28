package com.github.kongpf8848.rxhttp.request

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.github.kongpf8848.rxhttp.RxHttp
import com.github.kongpf8848.rxhttp.RxHttpTagManager
import com.github.kongpf8848.rxhttp.callback.HttpCallback
import okhttp3.RequestBody

abstract class AbsRequest {

    var context: Any? = null
    var url: String = ""
    private var ta: Any? = null
    private var param: Map<String, Any?>? = null
    protected var callback:HttpCallback<*>?=null

    protected abstract fun buildRequestBody(): RequestBody?

    constructor(context: Context) {
        this.context = context
    }

     constructor(fragment: Fragment) {
        context = fragment
    }

     constructor(activity: Activity) {
        context = activity
    }

    fun url(url: String): AbsRequest {
        this.url = url
        return this
    }

    fun tag(tag: Any?): AbsRequest {
        this.ta = tag
        return this
    }

    fun params(params: Map<String, Any?>?): AbsRequest {
        this.param = params
        return this
    }

    val actualContext: Context?
        get() {
            if (context is Context) {
                return context as Context
            } else if (context is Fragment) {
                return (context as Fragment).requireContext()
            }
            return null
        }

    fun getTag(): Any? {
        return if (ta == null) {
            RxHttpTagManager.generateRandomTag()
        } else ta
    }


    fun getParams(): Map<String, Any?>? {
        return param?.filterValues { it!=null }
    }

    fun <T> enqueue(callback: HttpCallback<T>) {
        this.callback=callback
        RxHttp.getInstance().enqueue(this, callback)
    }
}