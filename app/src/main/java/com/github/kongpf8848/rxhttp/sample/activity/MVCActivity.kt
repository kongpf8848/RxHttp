package com.github.kongpf8848.rxhttp.sample.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.github.kongpf8848.rxhttp.sample.R
import com.github.kongpf8848.rxhttp.sample.base.BaseActivity
import com.github.kongpf8848.rxhttp.sample.bean.Banner
import com.github.kongpf8848.rxhttp.sample.bean.User
import com.github.kongpf8848.rxhttp.sample.extension.getContent
import com.github.kongpf8848.rxhttp.sample.http.TKURL
import com.github.kongpf8848.rxhttp.sample.mvc.MVCApi
import com.github.kongpf8848.rxhttp.sample.mvc.MVCHttpCallback
import com.github.kongpf8848.rxhttp.sample.service.DownloadService
import com.github.kongpf8848.rxhttp.sample.utils.LogUtils
import kotlinx.android.synthetic.main.activity_mvvm.*
/**
 * MVC架构使用RxHttp示例
 */
class MVCActivity : BaseActivity() {

    private val params: Map<String, Any?>? = hashMapOf("name" to "jack", "location" to "shanghai", "age" to 28)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvc)

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

    private fun onButtonGet() {

        MVCApi.httpGet(context = baseActivity,
                url = TKURL.URL_GET,
                params = null,
                tag = null, callback = object : MVCHttpCallback<List<Banner>>() {
            override fun onStart() {
                LogUtils.d(TAG, "onButtonGet onStart() called")
            }

            override fun onSuccess(result: List<Banner>?) {
                Log.d(TAG, "onButtonGet onSuccess() called with: result = $result")
            }

            override fun onFailure(code: Int, msg: String?) {
                Log.d(TAG, "onButtonGet onFailure() called with: code = $code, msg = $msg")
            }

            override fun onComplete() {
                Log.d(TAG, "onButtonGet onComplete() called")
            }


        })
    }

    private fun onButtonPost() {
        MVCApi.httpPost(context = baseActivity,
                url = TKURL.URL_POST,
                params = params,
                tag = null,
                callback = object : MVCHttpCallback<User>() {
                    override fun onStart() {
                        super.onStart()
                        Log.d(TAG, "onButtonPost onStart() called")
                    }

                    override fun onSuccess(result: User?) {
                        Log.d(TAG, "onButtonPost onSuccess() called with: result = $result")
                    }

                    override fun onFailure(code: Int, msg: String?) {
                        Log.d(TAG, "onButtonPost onFailure() called with: code = $code, msg = $msg")
                    }

                    override fun onComplete() {
                        super.onComplete()
                        Log.d(TAG, "onButtonPost onComplete() called")
                    }
                })
    }

    private fun onButtonPostForm() {
        MVCApi.httpPostForm(context = baseActivity,
                url = TKURL.URL_POST_FORM,
                params = params,
                tag = null,
                callback = object : MVCHttpCallback<User>() {
                    override fun onStart() {
                        super.onStart()
                        Log.d(TAG, "onButtonPostForm onStart() called")
                    }

                    override fun onSuccess(result: User?) {
                        Log.d(TAG, "onButtonPostForm onSuccess() called with: result = $result")
                    }

                    override fun onFailure(code: Int, msg: String?) {
                        Log.d(TAG, "onButtonPostForm onFailure() called with: code = $code, msg = $msg")
                    }

                    override fun onComplete() {
                        super.onComplete()
                        Log.d(TAG, "onButtonPostForm onComplete() called")
                    }
                })
    }

    private fun onButtonPut() {
        MVCApi.httpPut(context = baseActivity,
                url = TKURL.URL_PUT,
                params = params,
                tag = null,
                callback = object : MVCHttpCallback<User>() {
                    override fun onStart() {
                        super.onStart()
                        Log.d(TAG, "onButtonPut onStart() called")
                    }

                    override fun onSuccess(result: User?) {
                        Log.d(TAG, "onButtonPut onSuccess() called with: result = $result")
                    }

                    override fun onFailure(code: Int, msg: String?) {
                        Log.d(TAG, "onButtonPut onFailure() called with: code = $code, msg = $msg")
                    }

                    override fun onComplete() {
                        super.onComplete()
                        Log.d(TAG, "onButtonPut onComplete() called")
                    }
                })
    }

    private fun onButtonDelete() {
        MVCApi.httpDelete(context = baseActivity,
                url = TKURL.URL_DELETE,
                params = params,
                tag = null,
                callback = object : MVCHttpCallback<User>() {
                    override fun onStart() {
                        super.onStart()
                        Log.d(TAG, "onButtonDelete onStart() called")
                    }

                    override fun onSuccess(result: User?) {
                        Log.d(TAG, "onButtonDelete onSuccess() called with: result = $result")
                    }

                    override fun onFailure(code: Int, msg: String?) {
                        Log.d(TAG, "onButtonDelete onFailure() called with: code = $code, msg = $msg")
                    }

                    override fun onComplete() {
                        super.onComplete()
                        Log.d(TAG, "onButtonDelete onComplete() called")
                    }
                })
    }

    private fun onButtonUpload() {
        getContent("image/*") {
            val map: MutableMap<String, Any> = HashMap()
            map["model"] = Build.MODEL
            map["manufacturer"] = Build.MANUFACTURER
            map["os"] = Build.VERSION.SDK_INT
            map["image"] = it

            MVCApi.httpUpload(context = baseActivity,
                    url = TKURL.URL_UPLOAD,
                    params = map,
                    tag = null,
                    callback = object : MVCHttpCallback<User>() {
                        override fun onStart() {
                            super.onStart()
                            Log.d(TAG, "onButtonUpload onStart() called")
                        }

                        override fun onProgress(readBytes: Long, totalBytes: Long) {
                            super.onProgress(readBytes, totalBytes)
                            Log.d(TAG, "onButtonUpload onProgress() called with: readBytes = $readBytes, totalBytes = $totalBytes")
                        }

                        override fun onSuccess(result: User?) {
                            Log.d(TAG, "onButtonUpload onSuccess() called with: result = $result")
                        }

                        override fun onFailure(code: Int, msg: String?) {
                            Log.d(TAG, "onButtonUpload onFailure() called with: code = $code, msg = $msg")
                        }

                        override fun onComplete() {
                            super.onComplete()
                            Log.d(TAG, "onButtonUpload onComplete() called")
                        }
                    })
        }
    }

    private fun onButtonDownload() {
        /**
         * 启动Service进行下载
         */
        val intent= Intent(this, DownloadService::class.java)
        intent.putExtra("url",TKURL.URL_DOWNLOAD_2)
        startService(intent)
    }
}