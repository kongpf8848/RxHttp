package com.github.kongpf8848.rxhttp.sample.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.github.kongpf8848.permissionhelper.PermissionHelper
import com.github.kongpf8848.rxhttp.bean.DownloadInfo
import com.github.kongpf8848.rxhttp.sample.R
import com.github.kongpf8848.rxhttp.sample.databinding.ActivityMainBinding
import com.github.kongpf8848.rxhttp.sample.extension.observeCallback
import com.github.kongpf8848.rxhttp.sample.mvvm.BaseMvvmActivity
import com.github.kongpf8848.rxhttp.sample.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : BaseMvvmActivity<MainViewModel, ActivityMainBinding>() {

    private var progressDialog: ProgressDialog? = null
    private val permissionHelper: PermissionHelper? = null
    private val PATH = Environment.getExternalStorageDirectory().absolutePath + File.separator
    private val REQUEST_CODE_STORAGE = 88
    private val REQUEST_CODE_INSTALL = 99
    private val REQUEST_CODE_PICK = 100
    private var downloadInfo: DownloadInfo? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        button1.setOnClickListener {
            onButtonGet()
        }
    }

    /**
     * GET请求
     */
    fun onButtonGet() {
        viewModel.getBannerList().observeCallback(this) {
            onStart {
                Log.d(TAG, "onButton1() onStart called")
            }
            onSuccess {
                Log.d(TAG, "onButton1() onSuccess called:${it}")
            }
            onFailure { code, msg ->
                Log.d(TAG, "onButton1() onFailure called with: code = $code, msg = $msg")
            }
            onComplete {
                Log.d(TAG, "onButton1() onComplete called")
            }
        }
    }


//    @OnClick(R.id.button2)
//    fun onButton2() {
//        val content = "this is post content"
//        RxHttp.getInstance()
//                .post(this)
//                .content(content)
//                .url(TKURL.URL_POST)
//                .enqueue(object : SimpleHttpCallback<String?>() {
//                    override fun onStart() {
//                        Log.d(TAG, "onStart")
//                    }
//
//                    override fun onNext(response: String) {
//                        Toast.makeText(this@MainActivity, "response:$response", Toast.LENGTH_SHORT).show()
//                    }
//
//                    override fun onError(e: Throwable) {
//                        Log.d(TAG, "onError:" + e.message)
//                        Toast.makeText(this@MainActivity, "error:" + e.message, Toast.LENGTH_SHORT).show()
//                    }
//
//                    override fun onComplete() {
//                        Log.d(TAG, "onComplete")
//                    }
//                })
//    }
//
//    @OnClick(R.id.button3)
//    fun onButton3() {
//        val map: MutableMap<String, Any> = HashMap()
//        map["model"] = Build.MODEL
//        map["manufacturer"] = Build.MANUFACTURER
//        map["os"] = Build.VERSION.SDK_INT
//        RxHttp.getInstance().postForm(this).url(TKURL.URL_POST_FORM).params(map).enqueue(object : SimpleHttpCallback<String?>() {
//            override fun onStart() {
//                Log.d(TAG, "onStart")
//            }
//
//            override fun onNext(response: String) {
//                Toast.makeText(this@MainActivity, "response:$response", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onError(e: Throwable) {
//                Log.d(TAG, "onError:" + e.message)
//                Toast.makeText(this@MainActivity, "error:" + e.message, Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onComplete() {
//                Log.d(TAG, "onComplete")
//            }
//        })
//    }
//
//    @OnClick(R.id.button4)
//    fun onButton4() {
//        download()
//        //        permissionHelper = new PermissionHelper(this, new PermissionHelper.OnPermissionListener() {
////            @Override
////            public void onPermissionGranted() {
////
////            }
////
////            @Override
////            public void onPermissionMissing(List<String> permissions) {
////
////            }
////
////            @Override
////            public void onPermissionFailed(List<PermissionInfomation> failList) {
////
////            }
////
////        });
////        permissionHelper.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE);
//    }
//
//    @OnClick(R.id.button5)
//    fun onButton5() {
//        registerForActivityResult<String, Uri>(GetContent(), ActivityResultCallback { result: Uri -> upload(result) }).launch("image/jpeg")
//    }
//
//    private fun upload(uri: Uri) {
//        val map: MutableMap<String, Any> = HashMap()
//        map["model"] = Build.MODEL
//        map["manufacturer"] = Build.MANUFACTURER
//        map["os"] = Build.VERSION.SDK_INT
//        map["image.jpg"] = uri
//        //        map.put("video",new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+File.separator+"gradle-4.4-all.zip"));
//        RxHttp.getInstance().upload(this).url(TKURL.URL_UPLOAD).params(map).enqueue(object : SimpleHttpCallback<String?>() {
//            override fun onStart() {
//                showProgressDialog("正在上传,请稍等...")
//            }
//
//            override fun onProgress(readBytes: Long, totalBytes: Long) {
//                updateProgress(readBytes, totalBytes)
//            }
//
//            override fun onNext(response: String) {
//                Log.d(TAG, "response:$response")
//                Toast.makeText(this@MainActivity, "response:$response", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onError(e: Throwable) {
//                Log.d(TAG, "onError:" + e.message)
//                Toast.makeText(this@MainActivity, "error:" + e.message, Toast.LENGTH_SHORT).show()
//                closeProgressDialog()
//            }
//
//            override fun onComplete() {
//                Log.d(TAG, "onComplete")
//                closeProgressDialog()
//            }
//        })
//    }
//
//    private fun download() {
//        val fileName = TKURL.URL_DOWNLOAD.substring(TKURL.URL_DOWNLOAD.lastIndexOf("/") + 1)
//        RxHttp.getInstance().download(this)
//                .dir(getExternalFilesDir(null).toString() + File.separator + "download")
//                .filename(fileName)
//                .breakpoint(false)
//                .url(TKURL.URL_DOWNLOAD)
//                .enqueue(object : DownloadCallback() {
//                    override fun onStart() {
//                        showProgressDialog("正在下载新版本,请稍等...")
//                    }
//
//                    override fun onProgress(downloadInfo: DownloadInfo) {
//                        if (downloadInfo != null) {
//                            Log.d(TAG, "onProgress() called with: downloadInfo = [$downloadInfo]")
//                            updateProgress(downloadInfo.progress, downloadInfo.total)
//                        }
//                    }
//
//                    override fun onNext(downloadInfo: DownloadInfo) {
//                        closeProgressDialog()
//                        this@MainActivity.downloadInfo = downloadInfo
//                        install()
//                    }
//
//                    override fun onError(e: Throwable) {
//                        Log.e(TAG, "onError:" + e.message)
//                        ToastHelper.toast(e.message)
//                        closeProgressDialog()
//                    }
//
//                    override fun onComplete() {
//                        Log.d(TAG, "onComplete:")
//                    }
//
//                    override fun onProgress(readBytes: Long, totalBytes: Long) {}
//                })
//    }
//
//    private fun showProgressDialog(title: String) {
//        progressDialog = ProgressDialog(this)
//        progressDialog!!.setMessage(title)
//        progressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
//        progressDialog!!.setCanceledOnTouchOutside(true)
//        progressDialog!!.setCancelable(true)
//        progressDialog!!.show()
//    }
//
//    private fun updateProgress(readBytes: Long, totalBytes: Long) {
//        Log.d(TAG, "updateProgress,readBytes:$readBytes,totalBytes:$totalBytes")
//        if (progressDialog != null && progressDialog!!.isShowing) {
//            progressDialog!!.progress = readBytes.toInt()
//            progressDialog!!.max = totalBytes.toInt()
//            val all = totalBytes * 1.0f / 1024 / 1024
//            val percent = readBytes * 1.0f / 1024 / 1024
//            progressDialog!!.setProgressNumberFormat(String.format("%.2fM/%.2fM", percent, all))
//        }
//    }
//
//    private fun closeProgressDialog() {
//        if (progressDialog != null) {
//            progressDialog!!.dismiss()
//        }
//    }
//
//    private fun install() {
//        val authority = "$packageName.fileprovider"
//        val file = File(downloadInfo!!.destDir, downloadInfo!!.fileName)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val pm = packageManager
//            if (pm.canRequestPackageInstalls()) {
//                ApkHelper.installApk(applicationContext, file, authority)
//            } else {
//                val uri = Uri.parse("package:$packageName")
//                val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, uri)
//                startActivityForResult(intent, REQUEST_CODE_INSTALL)
//            }
//        } else {
//            ApkHelper.installApk(applicationContext, file, authority)
//        }
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        permissionHelper?.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        permissionHelper?.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CODE_INSTALL) {
//            if (resultCode == Activity.RESULT_OK) {
//                val authority = "$packageName.fileprovider"
//                val file = File(downloadInfo!!.destDir, downloadInfo!!.fileName)
//                ApkHelper.installApk(applicationContext, file, authority)
//            }
//        } else if (requestCode == REQUEST_CODE_PICK) {
//            if (resultCode == Activity.RESULT_OK) {
//                val uri = data!!.data
//                var urlString = uri.toString()
//                urlString = Uri.decode(urlString)
//                val pre = "file://" + "/storage/emulated/0" + File.separator
//                var filePath: String? = null
//                if (urlString.startsWith(pre)) {
//                    filePath = Environment.getExternalStorageDirectory().absolutePath + File.separator + urlString.substring(pre.length)
//                }
//                if (TextUtils.isEmpty(filePath)) {
//                    filePath = ImageHelper.getImageAbsolutePath(this, uri)
//                }
//                Log.d(TAG, "filePath:$filePath")
//                if (!TextUtils.isEmpty(filePath)) {
//                    //upload(filePath);
//                }
//            }
//        }
//    }
//
//    @OnClick(R.id.button6)
//    fun onButton6() {
//        val content = "this is put content"
//        RxHttp.getInstance()
//                .put(this)
//                .content(content)
//                .url(TKURL.URL_PUT)
//                .enqueue(object : SimpleHttpCallback<String?>() {
//                    override fun onStart() {
//                        Log.d(TAG, "onStart")
//                    }
//
//                    override fun onNext(response: String) {
//                        Toast.makeText(this@MainActivity, "response:$response", Toast.LENGTH_SHORT).show()
//                    }
//
//                    override fun onError(e: Throwable) {
//                        Log.d(TAG, "onError:" + e.message)
//                        Toast.makeText(this@MainActivity, "error:" + e.message, Toast.LENGTH_SHORT).show()
//                    }
//
//                    override fun onComplete() {
//                        Log.d(TAG, "onComplete")
//                    }
//                })
//    }
//
//    @OnClick(R.id.button7)
//    fun onButton7() {
//    }
}
