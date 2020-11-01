package com.github.kongpf8848.rxhttp.sample.viewmodel

import android.content.Context
import com.github.kongpf8848.rxhttp.RxHttp
import com.github.kongpf8848.rxhttp.callback.HttpCallback
import com.github.kongpf8848.rxhttp.sample.bean.User

class UserViewModel : BaseViewModel<User>() {
    fun getUserData(context: Context, url: String) {
        RxHttp.getInstance().post(context).content("abcd")
                .url(url)
                .params(null).enqueue(object : HttpCallback<User>() {
                    override fun onStart() {
                        postStartEvent()
                    }

                    override fun onNext(response: User?) {
                        postSuccessEvent(response)
                    }

                    override fun onError(e: Throwable?) {
                        postErrorEvent(e)
                    }

                    override fun onComplete() {
                        postCompleteEvent()
                    }

                })
    }
}