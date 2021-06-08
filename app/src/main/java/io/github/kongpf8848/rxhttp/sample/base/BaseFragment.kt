package io.github.kongpf8848.rxhttp.sample.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import io.github.kongpf8848.rxhttp.sample.utils.LogUtils


open class BaseFragment : Fragment() {

    val TAG = javaClass.simpleName

    override fun onAttach(context: Context) {
        super.onAttach(context)
        LogUtils.d(TAG, "onAttach")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        LogUtils.d(TAG, "setUserVisibleHint:$isVisibleToUser")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        LogUtils.d(TAG, "onHiddenChanged:$hidden")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.d(TAG, "onCreate")
    }


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        LogUtils.d(TAG, "onViewCreated")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogUtils.d(TAG, "onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        LogUtils.d(TAG, "onStart")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        LogUtils.d(TAG, "onStop")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d(TAG, "onResume")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtils.d(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.d(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        LogUtils.d(TAG, "onDetach")
    }


}