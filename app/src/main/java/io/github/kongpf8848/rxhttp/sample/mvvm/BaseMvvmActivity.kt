package io.github.kongpf8848.rxhttp.sample.mvvm

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import io.github.kongpf8848.rxhttp.sample.base.BaseActivity
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * MVVM架构Activity基类
 */
abstract class BaseMvvmActivity<VM : BaseViewModel, VDB : ViewDataBinding> : BaseActivity(),
    IBaseMvvm {

    lateinit var viewModel: VM
    lateinit var binding: VDB


    final override fun onCreate(savedInstanceState: Bundle?) {
        onCreateStart(savedInstanceState)
        super.onCreate(savedInstanceState)

        binding =
            DataBindingUtil.setContentView(this, getLayoutId(applicationContext, viewBindingType()))
        binding.lifecycleOwner = this
        createViewModel()
        onCreateEnd(savedInstanceState)
    }

    protected open fun onCreateStart(savedInstanceState: Bundle?) {}
    protected open fun onCreateEnd(savedInstanceState: Bundle?) {}


    /**
     * 创建ViewModel
     */
    private fun createViewModel() {
        val type = findType(javaClass.genericSuperclass)
        val modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[0] as Class<VM>
        } else {
            BaseViewModel::class.java as Class<VM>
        }
        viewModel = ViewModelProvider(this)[modelClass]
    }

    private fun viewBindingType(): Type {
        val type = findType(javaClass.genericSuperclass)
        return if (type is ParameterizedType) {
            type.actualTypeArguments[1] as Class<VDB>
        } else {
            ViewDataBinding::class.java as Class<VDB>
        }
    }

}