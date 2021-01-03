package com.github.kongpf8848.rxhttp.sample.mvvm

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    protected val networkbaseRepository: NetworkRepository = NetworkRepository.instance
    protected var context: Context = application.applicationContext


}