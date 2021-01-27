package com.github.kongpf8848.rxhttp.sample.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.github.kongpf8848.rxhttp.sample.bean.Banner
import com.github.kongpf8848.rxhttp.sample.bean.User
import com.github.kongpf8848.rxhttp.sample.http.TKURL
import com.github.kongpf8848.rxhttp.sample.mvvm.BaseViewModel
import com.github.kongpf8848.rxhttp.sample.mvvm.TKState

class MainViewModel(application: Application) : BaseViewModel(application) {

    fun testGet(params: Map<String, Any?>?): MutableLiveData<TKState<List<Banner>>> {
        return networkbaseRepository.httpGet(
                context=context,
                url=TKURL.URL_GET,
                params = params
        )
    }

    fun testPost(params: Map<String, Any?>?,tag: Any?=null):MutableLiveData<TKState<User>>{
        return networkbaseRepository.httpPost(
                context=context,
                url=TKURL.URL_POST,
                params =params,
                tag=tag
        )
    }

    fun testPostForm(params: Map<String, Any?>?, tag: Any?=null):MutableLiveData<TKState<User>>{
        return networkbaseRepository.httpPostForm(
                context=context,
                url=TKURL.URL_POST_FORM,
                params =params,
                tag=tag
        )
    }

    fun testPut(params: Map<String, Any?>?,tag: Any?=null):MutableLiveData<TKState<User>>{
        return networkbaseRepository.httpPut(
                context=context,
                url=TKURL.URL_PUT,
                params =params,
                tag=tag
        )
    }

    fun testDelete(params: Map<String, Any?>?,tag: Any?=null):MutableLiveData<TKState<User>>{
        return networkbaseRepository.httpDelete(
                context=context,
                url=TKURL.URL_DELETE,
                params =params,
                tag=tag
        )
    }

    fun testUpload(params: Map<String, Any?>?,tag:Any?=null):MutableLiveData<TKState<String>> {
        return networkbaseRepository.httpUpload(
                context = context,
                url = TKURL.URL_UPLOAD,
                params = params,
                tag = tag
        )
    }

}