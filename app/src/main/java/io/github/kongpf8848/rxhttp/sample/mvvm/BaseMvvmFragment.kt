package io.github.kongpf8848.rxhttp.sample.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import io.github.kongpf8848.rxhttp.sample.base.BaseFragment
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * MVVM架构Fragment基类
 */
abstract class BaseMvvmFragment<VM : BaseViewModel, VDB : ViewDataBinding> : BaseFragment(),
    IBaseMvvm {

    lateinit var viewModel: VM
    lateinit var binding: VDB
    protected var rootView: View? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            binding = DataBindingUtil.inflate(
                inflater,
                getLayoutId(requireContext(), viewBindingType()),
                null,
                false
            )
            rootView = binding.root
            binding.lifecycleOwner = viewLifecycleOwner
            createViewModel()
        } else {
            val parent = rootView!!.parent as? ViewGroup
            parent?.removeView(rootView)
        }
        return rootView
    }


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