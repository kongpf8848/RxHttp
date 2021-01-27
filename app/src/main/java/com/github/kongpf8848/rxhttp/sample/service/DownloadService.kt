package com.github.kongpf8848.rxhttp.sample.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.text.TextUtils
import android.util.Log
import androidx.core.app.NotificationCompat
import com.github.kongpf8848.rxhttp.bean.DownloadInfo
import com.github.kongpf8848.rxhttp.callback.DownloadCallback
import com.github.kongpf8848.rxhttp.sample.R
import com.github.kongpf8848.rxhttp.sample.mvvm.NetworkRepository
import com.github.kongpf8848.rxhttp.sample.utils.LogUtils
import com.kongpf.commonhelper.ApkHelper
import com.kongpf.commonhelper.NotificationHelper
import com.kongpf.commonhelper.StorageHelper
import com.kongpf.commonhelper.ToastHelper
import com.kongpf.commonhelper.bean.NotificationInfo
import java.io.File
import java.text.DecimalFormat
import java.util.*

/**
 * 下载APK
 */
class DownloadService : Service() {

    private var url: String? = null
    lateinit var dir: String
    lateinit var filename: String

    lateinit var notificationBuilder: NotificationCompat.Builder
    lateinit var notificationManager: NotificationManager
    private var handlerThread: HandlerThread? = null
    private var handler: DownloadHandler? = null

    companion object {
        const val TAG = "DownloadService"
        const val DOWNLOAD_NOTIFY_ID = 1005
        const val MSG_SHOW_NOTIFICATION = 0
        const val MSG_UPDATE_NOTIFICATION = 1
        const val MSG_CANCEL_NOTIFICATION = 2
        const val MSG_INSTALL_APK = 3
    }


    /**
     * 处理更新通知栏的操作放在子线程操作，避免影响主线程
     */
    inner class DownloadHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_SHOW_NOTIFICATION -> {
                    notificationManager.notify(DOWNLOAD_NOTIFY_ID, notificationBuilder.build())
                }
                MSG_UPDATE_NOTIFICATION -> {
                    val pair = msg.obj as Pair<Long, Long>
                    val progress = pair.first
                    val total = pair.second
                    val percentFloat = DecimalFormat("0.00").format(progress * 1.0f / total)
                    val percentInt = (percentFloat.toDouble() * 100).toInt()
                    Log.d(TAG, "handleMessage() called with: progress = ${progress}, total = ${total},percentFloat:${percentFloat},percentInt:${percentInt}")
                    var content = ""
                    content = if (percentInt < 100) {
                        applicationContext.resources.getString(R.string.download_progress, percentInt)
                    } else {
                        applicationContext.resources.getString(R.string.download_success)
                    }
                    notificationBuilder.setContentText(content).setProgress(100, percentInt, false)
                    notificationManager.notify(DOWNLOAD_NOTIFY_ID, notificationBuilder.build())
                }
                MSG_CANCEL_NOTIFICATION -> {
                    notificationManager.cancel(DOWNLOAD_NOTIFY_ID)
                }
                MSG_INSTALL_APK -> {
                    ApkHelper.installApk(applicationContext, File(dir, filename), "$packageName.fileprovider")
                }
            }
        }
    }


    private var downloadCallback = object : DownloadCallback() {

        override fun onStart() {
            ToastHelper.toast("开始下载,可在通知栏查看进度")
            handler?.sendEmptyMessage(MSG_SHOW_NOTIFICATION)
        }


        override fun onProgress(readBytes: Long, totalBytes: Long) {
            LogUtils.d(TAG, "onProgress() called with: readBytes = $readBytes, totalBytes = $totalBytes")
            handler?.sendMessage(Message.obtain(handler, MSG_UPDATE_NOTIFICATION, Pair(readBytes, totalBytes)))

        }

        override fun onNext(response: DownloadInfo?) {
            LogUtils.d(TAG, "onNext() called with: response = $response")
            handler?.sendEmptyMessage(MSG_INSTALL_APK)
        }

        override fun onError(e: Throwable?) {
            LogUtils.d(TAG, "onError() called with: e = $e")
            handler?.sendEmptyMessage(MSG_CANCEL_NOTIFICATION)
        }

        override fun onComplete() {
            LogUtils.d(TAG, "onComplete() called")
            handler?.sendEmptyMessage(MSG_CANCEL_NOTIFICATION)
        }

    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onCreate() {
        super.onCreate()

        dir = StorageHelper.getExternalSandBoxPath(
                applicationContext,
                Environment.DIRECTORY_DOWNLOADS
        )

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationBuilder = NotificationHelper.getNotificationBuilder(
                applicationContext,
                NotificationInfo("001", "Category", "001", "Download")
        )
                .setOnlyAlertOnce(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(
                        PendingIntent.getActivity(
                                applicationContext,
                                0,
                                Intent(),
                                PendingIntent.FLAG_UPDATE_CURRENT
                        )
                )
                .setContentTitle("正在下载新版本,请稍等...")
                .setAutoCancel(true)
                .setOngoing(true)
                .setProgress(100, 0, false);


        val handlerThread = HandlerThread("DownloadHandlerThread")
        handlerThread.start()
        handler = DownloadHandler(handlerThread.looper)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        url = intent?.getStringExtra("url")
        url?.run {
            filename = substring(lastIndexOf(File.separator) + 1)
            if (TextUtils.isEmpty(filename)) {
                filename = UUID.randomUUID().toString()
            }
            NetworkRepository.instance.httpDownload(
                    context = applicationContext,
                    url = this,
                    dir = dir,
                    filename = filename,
                    callback = downloadCallback
            )
        }
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onDestroy() {
        super.onDestroy()
        handler?.removeCallbacksAndMessages(null)
        handlerThread?.quit()
    }


}