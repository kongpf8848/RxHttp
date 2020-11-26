package com.github.kongpf8848.rxhttp.sample.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.github.kongpf8848.rxhttp.sample.bean.Banner
import com.github.kongpf8848.rxhttp.sample.http.TKState
import com.github.kongpf8848.rxhttp.sample.http.TKURL
import com.github.kongpf8848.rxhttp.sample.mvvm.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel(application) {

    /**
     * 获取Banner信息
     */
    fun getBannerList(tag: Any? = null): MutableLiveData<TKState<List<Banner>>> {
        return networkbaseRepository.httpGet(
                context,
                TKURL.URL_BANNER,
                null,
                tag
        )
    }
}