package io.github.kongpf8848.rxhttp.sample.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.github.kongpf8848.rxhttp.sample.mvvm.TKState

fun <T> LiveData<TKState<T>>.observeState(lifecycleOwner: LifecycleOwner, callback: TKState.HandleCallback<T>.() -> Unit) {
    this.observe(lifecycleOwner, Observer {
        it.handle(callback)
    })
}

fun <T> LiveData<TKState<T>>.observeStateForever(callback: TKState.HandleCallback<T>.() -> Unit) {
    this.observeForever {
        it.handle(callback)
    }
}