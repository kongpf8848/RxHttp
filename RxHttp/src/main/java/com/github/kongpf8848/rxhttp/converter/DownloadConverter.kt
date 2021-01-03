package com.github.kongpf8848.rxhttp.converter

import com.github.kongpf8848.rxhttp.Platform
import com.github.kongpf8848.rxhttp.ProgressResponseBody
import com.github.kongpf8848.rxhttp.bean.DownloadInfo
import com.github.kongpf8848.rxhttp.callback.DownloadCallback
import com.github.kongpf8848.rxhttp.callback.ProgressCallback
import com.github.kongpf8848.rxhttp.request.DownloadRequest
import okhttp3.ResponseBody
import okio.Okio
import java.io.File
import java.io.FileOutputStream
import java.lang.reflect.Type

class DownloadConverter<T>(private val downloadRequest: DownloadRequest, private val callback: DownloadCallback) : IConverter<T> {
    private val downloadInfo: DownloadInfo

    @Throws(Exception::class)
    override fun convert(body: ResponseBody, type: Type): T {
        try {
            val fileDir = File(downloadInfo.destDir)
            if (!fileDir.exists()) {
                fileDir.mkdirs()
            }
            val file = File(downloadInfo.destDir, downloadInfo.fileName)
            val currentProgress: Long
            currentProgress = if (downloadRequest.isBreakpoint) {
                if (file.exists()) {
                    file.length()
                } else {
                    0
                }
            } else {
                0
            }
            val fos = FileOutputStream(file, downloadRequest.isBreakpoint)
            val progressResponseBody = ProgressResponseBody(body, object : ProgressCallback {
                override fun onProgress(readBytes: Long, totalBytes: Long) {
                    downloadInfo.progress = currentProgress + readBytes
                    Platform.get().defaultCallbackExecutor()!!.execute(Runnable { callback.onProgress(downloadInfo) })
                }
            })
            downloadInfo.total = currentProgress + progressResponseBody.contentLength()
            Platform.Companion.get().defaultCallbackExecutor()!!.execute(Runnable { callback.onProgress(downloadInfo) })
            val sink = Okio.buffer(Okio.sink(fos))
            sink.writeAll(progressResponseBody.source())
            sink.flush()
            sink.close()
            progressResponseBody.close()
        } catch (e: Exception) {
            throw e
        }
        return downloadInfo as T
    }

    init {
        downloadInfo = DownloadInfo(downloadRequest.url!!, downloadRequest.dir!!, downloadRequest.filename!!)
    }
}