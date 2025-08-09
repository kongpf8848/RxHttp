package io.github.kongpf8848.rxhttp.sample.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import io.github.kongpf8848.rxhttp.sample.bean.Banner
import io.github.kongpf8848.rxhttp.sample.bean.Feed
import io.github.kongpf8848.rxhttp.sample.bean.User
import io.github.kongpf8848.rxhttp.sample.http.TKURL
import io.github.kongpf8848.rxhttp.sample.mvvm.BaseViewModel
import io.github.kongpf8848.rxhttp.sample.mvvm.TKState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainViewModel(application: Application) : BaseViewModel(application) {


    fun testGet(params: Map<String, Any?>?): MutableLiveData<TKState<List<Banner>>> {

        return networkbaseRepository.httpGet(
            context = context,
            url = TKURL.URL_GET,
            params = params
        )
    }

    fun testPost(params: Map<String, Any?>?, tag: Any? = null): MutableLiveData<TKState<User>> {
        return networkbaseRepository.httpPost(
            context = context,
            url = TKURL.URL_POST,
            params = params,
            tag = tag
        )
    }

    fun testPostForm(params: Map<String, Any?>?, tag: Any? = null): MutableLiveData<TKState<User>> {
        return networkbaseRepository.httpPostForm(
            context = context,
            url = TKURL.URL_POST_FORM,
            params = params,
            tag = tag
        )
    }

    fun testPut(params: Map<String, Any?>?, tag: Any? = null): MutableLiveData<TKState<User>> {
        return networkbaseRepository.httpPut(
            context = context,
            url = TKURL.URL_PUT,
            params = params,
            tag = tag
        )
    }

    fun testDelete(params: Map<String, Any?>?, tag: Any? = null): MutableLiveData<TKState<User>> {
        return networkbaseRepository.httpDelete(
            context = context,
            url = TKURL.URL_DELETE,
            params = params,
            tag = tag
        )
    }

    fun testUpload(params: Map<String, Any?>?, tag: Any? = null): MutableLiveData<TKState<String>> {
        return networkbaseRepository.httpUpload(
            context = context,
            url = TKURL.URL_UPLOAD,
            params = params,
            tag = tag
        )
    }

    interface API {
        @GET("api/4/stories/latest")
        suspend fun getArticles(): Feed
    }

    private fun getArticles(): Flow<Feed> = flow {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://news-at.zhihu.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(API::class.java)
        val feed = api.getArticles()
        println("Resume on ${Thread.currentThread().name}")
        emit(feed)
    }


}