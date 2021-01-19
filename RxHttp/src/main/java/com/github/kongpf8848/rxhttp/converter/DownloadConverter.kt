package com.github.kongpf8848.rxhttp.converter

import com.github.kongpf8848.rxhttp.Platform
import com.github.kongpf8848.rxhttp.ProgressResponseBody
import com.github.kongpf8848.rxhttp.bean.DownloadInfo
import com.github.kongpf8848.rxhttp.callback.DownloadCallback
import com.github.kongpf8848.rxhttp.callback.ProgressCallback
import com.github.kongpf8848.rxhttp.request.DownloadRequest
import okhttp3.ResponseBody
import okio.buffer
import okio.sink
import java.io.File
import java.io.FileOutputStream
import java.lang.reflect.Type

class DownloadConverter<T>(private val downloadRequest: DownloadRequest, private val callback: DownloadCallback) : IConverter<T> {

    private val downloadInfo= DownloadInfo(downloadRequest.url, downloadRequest.dir, downloadRequest.filename)

    @Throws(Exception::class)
    override fun convert(body: ResponseBody, type: Type): T {
        try {
            val fileDir = File(downloadInfo.dir)
            if (!fileDir.exists()) {
                fileDir.mkdirs()
            }
            val file = File(downloadInfo.dir, downloadInfo.fileName)
            val currentProgress= if (downloadRequest.isBreakpoint) {
                if (file.exists()) {
                    file.length()
                } else {
                    0L
                }
            } else {
                0L
            }
            val fos = FileOutputStream(file, downloadRequest.isBreakpoint)
            val progressResponseBody = ProgressResponseBody(body, object : ProgressCallback {
                override fun onProgress(readBytes: Long, totalBytes: Long) {
                    downloadInfo.progress=currentProgress+readBytes
                    Platform.get().defaultCallbackExecutor()!!.execute { callback.onProgress(downloadInfo.progress, currentProgress+totalBytes) }
                }
            })
            downloadInfo.total = currentProgress + progressResponseBody.contentLength()
            Platform.get().defaultCallbackExecutor()?.execute { callback.onProgress(currentProgress,downloadInfo.total) }
            val sink = fos.sink().buffer()
            sink.writeAll(progressResponseBody.source())
            sink.flush()
            sink.close()
            progressResponseBody.close()
        } catch (e: Exception) {
            throw e
        }
        return downloadInfo as T
    }

}