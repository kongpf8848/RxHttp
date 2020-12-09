package com.github.kongpf8848.rxhttp.sample.activity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.github.kongpf8848.rxhttp.RxHttp
import com.github.kongpf8848.rxhttp.bean.DownloadInfo
import com.github.kongpf8848.rxhttp.callback.SimpleHttpCallback
import com.github.kongpf8848.rxhttp.sample.R
import com.github.kongpf8848.rxhttp.sample.databinding.ActivityMainBinding
import com.github.kongpf8848.rxhttp.sample.extension.getContent
import com.github.kongpf8848.rxhttp.sample.extension.observeCallback
import com.github.kongpf8848.rxhttp.sample.http.TKURL
import com.github.kongpf8848.rxhttp.sample.mvvm.BaseMvvmActivity
import com.github.kongpf8848.rxhttp.sample.service.DownloadService
import com.github.kongpf8848.rxhttp.sample.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : BaseMvvmActivity<MainViewModel, ActivityMainBinding>() {

    private var progressDialog: ProgressDialog? = null
    private val PATH = Environment.getExternalStorageDirectory().absolutePath + File.separator
    private var downloadInfo: DownloadInfo? = null


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
                Log.d(TAG, "onButtonGet() onFailure called with: code = $code, msg = $msg")
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

        viewModel.testPost().observeCallback(this) {
            onStart {
                Log.d(TAG, "onButtonPost() onStart called")
            }
            onSuccess {
                Log.d(TAG, "onButtonPost() onSuccess called:${it}")
            }
            onFailure { code, msg ->
                Log.d(TAG, "onButtonPost() onFailure called with: code = $code, msg = $msg")
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

        viewModel.testPostForm().observeCallback(this) {
            onStart {
                Log.d(TAG, "onButtonPostForm() onStart called")
            }
            onSuccess {
                Log.d(TAG, "onButtonPostForm() onSuccess called:${it}")
            }
            onFailure { code, msg ->
                Log.d(TAG, "onButtonPostForm() onFailure called with: code = $code, msg = $msg")
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

        viewModel.testPut().observeCallback(this) {
            onStart {
                Log.d(TAG, "onButtonPut() onStart called")
            }
            onSuccess {
                Log.d(TAG, "onButtonPut() onSuccess called:${it}")
            }
            onFailure { code, msg ->
                Log.d(TAG, "onButtonPut() onFailure called with: code = $code, msg = $msg")
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

        viewModel.testDelete().observeCallback(this) {
            onStart {
                Log.d(TAG, "onButtonDelete() onStart called")
            }
            onSuccess {
                Log.d(TAG, "onButtonDelete() onSuccess called:${it}")
            }
            onFailure { code, msg ->
                Log.d(TAG, "onButtonDelete() onFailure called with: code = $code, msg = $msg")
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
        RxHttp.getInstance().upload(this).url(TKURL.URL_UPLOAD).params(map).enqueue(object : SimpleHttpCallback<String>() {
            override fun onStart() {
                showProgressDialog("正在上传,请稍等...")
            }

            override fun onProgress(readBytes: Long, totalBytes: Long) {
                updateProgress(readBytes, totalBytes)
            }

            override fun onNext(response: String) {
                Log.d(TAG, "response:$response")
                Toast.makeText(this@MainActivity, "response:$response", Toast.LENGTH_SHORT).show()
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError:" + e.message)
                Toast.makeText(this@MainActivity, "error:" + e.message, Toast.LENGTH_SHORT).show()
                closeProgressDialog()
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete")
                closeProgressDialog()
            }
        })
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
        progressDialog!!.setMessage(title)
        progressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        progressDialog!!.setCanceledOnTouchOutside(true)
        progressDialog!!.setCancelable(true)
        progressDialog!!.show()
    }

    private fun updateProgress(readBytes: Long, totalBytes: Long) {
        Log.d(TAG, "updateProgress,readBytes:$readBytes,totalBytes:$totalBytes")
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.progress = readBytes.toInt()
            progressDialog!!.max = totalBytes.toInt()
            val all = totalBytes * 1.0f / 1024 / 1024
            val percent = readBytes * 1.0f / 1024 / 1024
            progressDialog!!.setProgressNumberFormat(String.format("%.2fM/%.2fM", percent, all))
        }
    }

    private fun closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog!!.dismiss()
        }
    }
}





