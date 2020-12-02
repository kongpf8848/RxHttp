package com.github.kongpf8848.rxhttp.sample.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.github.kongpf8848.rxhttp.sample.http.TKState

fun <T> LiveData<TKState<T>>.observeCallback(lifecycleOwner:LifecycleOwner, callback: TKState.HandleCallback<T>.() -> Unit){
    this.observe(lifecycleOwner, Observer {
        it.handle(callback)
    })
}