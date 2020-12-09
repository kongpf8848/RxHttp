package com.github.kongpf8848.rxhttp.sample.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.github.kongpf8848.rxhttp.sample.bean.Banner
import com.github.kongpf8848.rxhttp.sample.bean.User
import com.github.kongpf8848.rxhttp.sample.http.TKState
import com.github.kongpf8848.rxhttp.sample.http.TKURL
import com.github.kongpf8848.rxhttp.sample.mvvm.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel(application) {

    fun testGet(tag: Any? = null): MutableLiveData<TKState<List<Banner>>> {
        return networkbaseRepository.httpGet(
                context=context,
                url=TKURL.URL_GET,
                params = null,
                tag=tag
        )
    }

    fun testPost(tag: Any?=null):MutableLiveData<TKState<User>>{
        val map= hashMapOf("name" to  "jack", "location" to "shanghai", "age" to 28)
        return networkbaseRepository.httpPost(
                context=context,
                url=TKURL.URL_POST,
                params =map,
                tag=tag
        )
    }

    fun testPostForm(tag: Any?=null):MutableLiveData<TKState<User>>{
        val map= hashMapOf("name" to  "jack2", "location" to "shanghai2", "age" to 24)
        return networkbaseRepository.httpPostForm(
                context=context,
                url=TKURL.URL_POST_FORM,
                params =map,
                tag=tag
        )
    }

    fun testPut(tag: Any?=null):MutableLiveData<TKState<User>>{
        val map= hashMapOf("name" to  "jack2", "location" to "shanghai2", "age" to 24)
        return networkbaseRepository.httpPut(
                context=context,
                url=TKURL.URL_PUT,
                params =map,
                tag=tag
        )
    }

    fun testDelete(tag: Any?=null):MutableLiveData<TKState<User>>{
        val map= hashMapOf("name" to  "jack2", "location" to "shanghai2", "age" to 24)
        return networkbaseRepository.httpDelete(
                context=context,
                url=TKURL.URL_DELETE,
                params =map,
                tag=tag
        )
    }

    fun testHead(){

    }
}