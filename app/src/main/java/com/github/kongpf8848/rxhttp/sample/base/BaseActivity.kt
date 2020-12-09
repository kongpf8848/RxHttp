package com.github.kongpf8848.rxhttp.sample.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.kongpf8848.rxhttp.sample.utils.LogUtils


open class BaseActivity : AppCompatActivity() {

    val TAG=javaClass.simpleName
    lateinit var baseActivity: BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseActivity=this
        LogUtils.d(TAG, "onCreate called")
    }

    override fun onStart() {
        super.onStart()
        LogUtils.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        LogUtils.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.d(TAG, "onDestroy() called")
    }

    override fun onRestart() {
        super.onRestart()
        LogUtils.d(TAG, "onRestart() called")
    }

    override fun onContentChanged() {
        super.onContentChanged()
        LogUtils.d(TAG, "onContentChanged() called")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        LogUtils.d(TAG, "onNewIntent() called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        LogUtils.d(TAG, "onSaveInstanceState() called")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        LogUtils.d(TAG, "onRestoreInstanceState() called")
    }

}