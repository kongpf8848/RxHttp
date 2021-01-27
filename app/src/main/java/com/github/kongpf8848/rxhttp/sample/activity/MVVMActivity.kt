package com.github.kongpf8848.rxhttp.sample.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.github.kongpf8848.rxhttp.sample.R
import com.github.kongpf8848.rxhttp.sample.databinding.ActivityMainBinding
import com.github.kongpf8848.rxhttp.sample.extension.getContent
import com.github.kongpf8848.rxhttp.sample.extension.observeState
import com.github.kongpf8848.rxhttp.sample.http.TKURL
import com.github.kongpf8848.rxhttp.sample.mvvm.BaseMvvmActivity
import com.github.kongpf8848.rxhttp.sample.service.DownloadService
import com.github.kongpf8848.rxhttp.sample.utils.LogUtils
import com.github.kongpf8848.rxhttp.sample.viewmodel.MainViewModel
import com.kongpf.commonhelper.ToastHelper
import kotlinx.android.synthetic.main.activity_mvvm.*

/**
 * MVVM架构使用RxHttp示例
 */
class MVVMActivity : BaseMvvmActivity<MainViewModel, ActivityMainBinding>() {

    private var progressDialog: ProgressDialog? = null


    private val params:Map<String,Any?>?= hashMapOf("name" to  "jack", "location" to "shanghai", "age" to 28)

    override fun getLayoutId(): Int {
        return R.layout.activity_mvvm
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
    private fun onButtonGet() {
        viewModel.testGet(null).observeState(this) {
            onStart {
                LogUtils.d(TAG, "onButtonGet() onStart called")
            }
            onSuccess {
                LogUtils.d(TAG, "onButtonGet() onSuccess called:${it}")
            }
            onFailure { code, msg ->
                ToastHelper.toast("onButtonGet() onFailure,code:${code},msg:${msg}")
            }
            onComplete {
                LogUtils.d(TAG, "onButtonGet() onComplete called")
            }
        }

    }

    /**
     * POST请求
     */
    private fun onButtonPost() {

        viewModel.testPost(params).observeState(this) {
            onStart {
                LogUtils.d(TAG, "onButtonPost() onStart called")
            }
            onSuccess {
                LogUtils.d(TAG, "onButtonPost() onSuccess called:${it}")
            }
            onFailure { code, msg ->
                ToastHelper.toast("onButtonPost() onFailure,code:${code},msg:${msg}")
            }
            onComplete {
                LogUtils.d(TAG, "onButtonPost() onComplete called")
            }
        }
    }

    /**
     * POST FORM请求
     */
    private fun onButtonPostForm() {

        viewModel.testPostForm(params).observeState(this) {
            onStart {
                LogUtils.d(TAG, "onButtonPostForm() onStart called")
            }
            onSuccess {
                LogUtils.d(TAG, "onButtonPostForm() onSuccess called:${it}")
            }
            onFailure { code, msg ->
                ToastHelper.toast("onButtonPostForm onFailure,code:${code},msg:${msg}")
            }
            onComplete {
                LogUtils.d(TAG, "onButtonPostForm() onComplete called")
            }
        }
    }

    /**
     * PUT请求
     */
    private fun onButtonPut() {

        viewModel.testPut(params).observeState(this) {
            onStart {
                LogUtils.d(TAG, "onButtonPut() onStart called")
            }
            onSuccess {
                LogUtils.d(TAG, "onButtonPut() onSuccess called:${it}")
            }
            onFailure { code, msg ->
                ToastHelper.toast("onButtonPut() onFailure,code:${code},msg:${msg}")
            }
            onComplete {
                LogUtils.d(TAG, "onButtonPut() onComplete called")
            }
        }
    }

    /**
     * DELETE请求
     */
    private fun onButtonDelete() {

        viewModel.testDelete(params).observeState(this) {
            onStart {
                LogUtils.d(TAG, "onButtonDelete() onStart called")
            }
            onSuccess {
                LogUtils.d(TAG, "onButtonDelete() onSuccess called:${it}")
            }
            onFailure { code, msg ->
                ToastHelper.toast("onButtonDelete() onFailure,code:${code},msg:${msg}")
            }
            onComplete {
                LogUtils.d(TAG, "onButtonDelete() onComplete called")
            }
        }
    }

    /**
     * 上传,实在找不到如何很好的方法去模拟演示上传的过程,亲，要不咱搭建一个Tomcat服务器,然后再写一个上传接口，自己动手，丰衣足食
     */
    private fun onButtonUpload() {
        getContent("image/*") {
            val map: MutableMap<String, Any> = HashMap()
            map["model"] = Build.MODEL
            map["manufacturer"] = Build.MANUFACTURER
            map["os"] = Build.VERSION.SDK_INT
            map["image"] = it

            viewModel.testUpload(map).observeState(this){
                onStart{
                    LogUtils.d(TAG, "onButtonUpload() onStart called")
                    showProgressDialog("正在上传,请稍等...")
                }

                onProgress { progress, total ->
                    LogUtils.d(TAG, "onButtonUpload() onProgress called with: progress = $progress, total = $total")
                    updateProgress(progress, total)
                }

                onSuccess {
                    LogUtils.d(TAG, "onButtonUpload() onSuccess called:${it}")
                    ToastHelper.toast("上传成功,返回结果:${it}")
                }

                onFailure { code, msg ->
                    LogUtils.d(TAG, "onButtonUpload() onFailure called with: code = $code, msg = $msg")
                    ToastHelper.toast("上传失败,code:${code},msg:${msg}")
                }

                onComplete{
                    LogUtils.d(TAG, "onButtonUpload() onComplete called")
                    closeProgressDialog()
                }
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
        intent.putExtra("url",TKURL.URL_DOWNLOAD_2)
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
                setProgressNumberFormat(String.format("%.2fM/%.2fM", progress * 1.0f / 1024 / 1024, total * 1.0f / 1024 / 1024))
            }
        }
    }

    private fun closeProgressDialog() {
        progressDialog?.dismiss()
    }
}





