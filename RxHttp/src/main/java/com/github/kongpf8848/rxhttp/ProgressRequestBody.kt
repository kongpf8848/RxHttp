package com.github.kongpf8848.rxhttp

import com.github.kongpf8848.rxhttp.callback.ProgressCallback
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.*
import java.io.IOException

class ProgressRequestBody(private val requestBody: RequestBody, private val callback: ProgressCallback?) : RequestBody() {

    private var progressSink: BufferedSink? = null
    private var contentLength = 0L

    override fun contentType(): MediaType? {
        return requestBody.contentType()
    }

    @Throws(IOException::class)
    override fun contentLength(): Long {
        if (contentLength == 0L) {
            contentLength = requestBody.contentLength()
        }
        return contentLength
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        if (callback == null) {
            requestBody.writeTo(sink)
            return
        }
        if (progressSink == null) {
            progressSink = Okio.buffer(sink(sink))
        }
        callback.onProgress(0, contentLength())
        requestBody.writeTo(progressSink)
        progressSink?.apply {
            flush()
            close()
        }
    }

    private fun sink(sink: Sink): Sink {
        return object : ForwardingSink(sink) {
            var totalBytesWrite = 0L
            var lastBytesWrite = 0L

            @Throws(IOException::class)
            override fun write(source: Buffer, byteCount: Long) {
                super.write(source, byteCount)
                lastBytesWrite = totalBytesWrite
                totalBytesWrite += byteCount
                if (totalBytesWrite != lastBytesWrite) {
                    callback?.onProgress(totalBytesWrite, contentLength())
                }
            }
        }
    }

}