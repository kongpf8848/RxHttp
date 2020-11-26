package com.github.kongpf8848.rxhttp.sample.mvvm

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.github.kongpf8848.rxhttp.sample.base.BaseActivity
import java.lang.reflect.ParameterizedType

abstract class BaseMvvmActivity<VM : BaseViewModel, VDB : ViewDataBinding> : BaseActivity(){

    lateinit var viewModel: VM
    lateinit var binding: VDB

    protected abstract fun getLayoutId(): Int
    final override fun onCreate(savedInstanceState: Bundle?) {
        onCreateStart(savedInstanceState)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.setLifecycleOwner(this)
        createViewModel()
        onCreateEnd(savedInstanceState)
    }

    protected open fun onCreateStart(savedInstanceState: Bundle?) {}
    protected open fun onCreateEnd(savedInstanceState: Bundle?) {}

    /**
     * 创建ViewModel
     */
    private fun createViewModel() {
        val type = javaClass.genericSuperclass
        val modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[0] as Class<VM>
        } else {
            BaseViewModel::class.java as Class<VM>
        }
        viewModel = ViewModelProviders.of(this).get(modelClass)
    }

}