package com.github.kongpf8848.rxhttp.sample.activity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.github.kongpf8848.rxhttp.sample.R
import com.github.kongpf8848.rxhttp.sample.databinding.ActivityMainBinding
import com.github.kongpf8848.rxhttp.sample.extension.getContent
import com.github.kongpf8848.rxhttp.sample.extension.observeCallback
import com.github.kongpf8848.rxhttp.sample.http.TKURL
import com.github.kongpf8848.rxhttp.sample.mvvm.BaseMvvmActivity
import com.github.kongpf8848.rxhttp.sample.service.DownloadService
import com.github.kongpf8848.rxhttp.sample.utils.LogUtils
import com.github.kongpf8848.rxhttp.sample.viewmodel.MainViewModel
import com.kongpf.commonhelper.ToastHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvvmActivity<MainViewModel, ActivityMainBinding>() {

    private var progressDialog: ProgressDialog? = null


    private val params= hashMapOf("name" to  "jack", "location" to "shanghai", "age" to 28)

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        button1.setOnClickListener {
            onButtonGet()
        }
        button2.setOnClickListener {
            onButtonPost()
        }
        button3.setOnClickListener {
            onButtonPostForm()
        }
        button4.setOnClickListener {
            onButtonPut()
        }
        button5.setOnClickListener {
            onButtonDelete()
        }
        button6.setOnClickListener {
            onButtonUpload()
        }
        button7.setOnClickListener {
            onButtonDownload()
        }
    }

    /**
     * GET请求
     */
    fun onButtonGet() {
        viewModel.testGet().observeCallback(this) {
            onStart {
                Log.d(TAG, "onButtonGet() onStart called")
            }
            onSuccess {
                Log.d(TAG, "onButtonGet() onSuccess called:${it}")
            }
            onFailure { code, msg ->
                ToastHelper.toast("失败,code:${code},msg:${msg}")
            }
            onComplete {
                Log.d(TAG, "onButtonGet() onComplete called")
            }
        }
    }

    /**
     * POST请求
     */
    fun onButtonPost() {

        viewModel.testPost(params).observeCallback(this) {
            onStart {
                Log.d(TAG, "onButtonPost() onStart called")
            }
            onSuccess {
                Log.d(TAG, "onButtonPost() onSuccess called:${it}")
            }
            onFailure { code, msg ->
                ToastHelper.toast("失败,code:${code},msg:${msg}")
            }
            onComplete {
                Log.d(TAG, "onButtonPost() onComplete called")
            }
        }
    }

    /**
     * POST FORM请求
     */
    fun onButtonPostForm() {

        viewModel.testPostForm(params).observeCallback(this) {
            onStart {
                Log.d(TAG, "onButtonPostForm() onStart called")
            }
            onSuccess {
                Log.d(TAG, "onButtonPostForm() onSuccess called:${it}")
            }
            onFailure { code, msg ->
                ToastHelper.toast("失败,code:${code},msg:${msg}")
            }
            onComplete {
                Log.d(TAG, "onButtonPostForm() onComplete called")
            }
        }
    }

    /**
     * PUT请求
     */
    fun onButtonPut() {

        viewModel.testPut(params).observeCallback(this) {
            onStart {
                Log.d(TAG, "onButtonPut() onStart called")
            }
            onSuccess {
                Log.d(TAG, "onButtonPut() onSuccess called:${it}")
            }
            onFailure { code, msg ->
                ToastHelper.toast("失败,code:${code},msg:${msg}")
            }
            onComplete {
                Log.d(TAG, "onButtonPut() onComplete called")
            }
        }
    }

    /**
     * DELETE请求
     */
    fun onButtonDelete() {

        viewModel.testDelete(params).observeCallback(this) {
            onStart {
                Log.d(TAG, "onButtonDelete() onStart called")
            }
            onSuccess {
                Log.d(TAG, "onButtonDelete() onSuccess called:${it}")
            }
            onFailure { code, msg ->
                ToastHelper.toast("失败,code:${code},msg:${msg}")
            }
            onComplete {
                Log.d(TAG, "onButtonDelete() onComplete called")
            }
        }
    }

    /**
     * 上传
     */
    fun onButtonUpload() {
        getContent("image/*") {
            upload(it)
        }
    }

    private fun upload(uri: Uri) {
        val map: MutableMap<String, Any> = HashMap()
        map["model"] = Build.MODEL
        map["manufacturer"] = Build.MANUFACTURER
        map["os"] = Build.VERSION.SDK_INT
        map["image"] = uri

        viewModel.testUpload(map).observeCallback(this){
            onStart{
                showProgressDialog("正在上传,请稍等...")
            }

            onProgress { progress, total ->
                updateProgress(progress, total)
            }

            onSuccess {
                ToastHelper.toast("上传成功,返回结果:${it}")
            }

            onFailure { code, msg ->
                ToastHelper.toast("上传失败,code:${code},msg:${msg}")
            }

            onComplete{
                closeProgressDialog()
            }
        }

    }

    /**
     * 下载
     */
    fun onButtonDownload() {
        /**
         * 启动Service进行下载
         */
        val intent= Intent(this, DownloadService::class.java)
        intent.putExtra("url",TKURL.URL_DOWNLOAD)
        startService(intent)
    }

    private fun showProgressDialog(title: String) {
        progressDialog = ProgressDialog(this)
        progressDialog?.apply {
            setMessage(title)
            setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            setCanceledOnTouchOutside(false)
            setCancelable(false)
            show()
        }
    }

    private fun updateProgress(progress: Long, total: Long) {
        LogUtils.d(TAG, "updateProgress,readBytes:$progress,totalBytes:$total")
        progressDialog?.apply {
            if(isShowing){
                this.progress = progress.toInt()
                this.max=total.toInt()
                val all = total * 1.0f / 1024 / 1024
                val percent = progress * 1.0f / 1024 / 1024
                setProgressNumberFormat(String.format("%.2fM/%.2fM", percent, all))
            }
        }
    }

    private fun closeProgressDialog() {
        progressDialog?.dismiss()
    }
}





