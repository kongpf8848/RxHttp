package com.github.kongpf8848.rxhttp.sample.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

open class BaseViewModel<T>:ViewModel() {

    protected var successCallback: ((T?) -> Unit)? = null
    protected var errorCallback: ((Throwable?) -> Unit)? = null
    protected var startCallback: ((Any?) -> Unit)? = null
    protected var completeCallback: ((Any?) -> Unit)? = null

    protected val successLiveData: MutableLiveData<T> = MutableLiveData()
    protected val errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    protected val startLiveData: MutableLiveData<Any?> = MutableLiveData()
    protected val completeLiveData: MutableLiveData<Any?> = MutableLiveData()

    fun init(owner: LifecycleOwner):BaseViewModel<T>{
        successLiveData.observe(owner, Observer {
            successCallback?.invoke(it)
        })
        errorLiveData.observe(owner, Observer {
            errorCallback?.invoke(it)
        })
        startLiveData.observe(owner, Observer {
            startCallback?.invoke(it)
        })
        completeLiveData.observe(owner, Observer {
            completeCallback?.invoke(it)
        })
        return this
    }

    fun postStartEvent(){
        startLiveData.postValue(Any())
    }
    fun postSuccessEvent(data:(T?)){
        successLiveData.postValue(data)
    }
    fun postErrorEvent(data:(Throwable?)){
        errorLiveData.postValue(data)
    }
    fun postCompleteEvent(){
        completeLiveData.postValue(Any())
    }

    fun onSuccess(callback: (T?) -> Unit) {
        successCallback = callback
    }

    fun onError( callback: (Throwable?) -> Unit) {
        errorCallback=callback
    }

    fun onStart(callback: (Any?) -> Unit) {
        startCallback = callback
    }

    fun onComplete( callback: (Any?) -> Unit) {
        completeCallback=callback
    }

}