package io.github.kongpf8848.rxhttp

import io.github.kongpf8848.rxhttp.callback.ProgressCallback
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*
import java.io.IOException

class ProgressResponseBody(private val responseBody: ResponseBody, private val callback: ProgressCallback?) : ResponseBody() {

    private var progressSource: BufferedSource? = null
    private var contentLength = 0L

    override fun contentType(): MediaType? {
        return responseBody.contentType()
    }

    override fun contentLength(): Long {
        if (contentLength == 0L) {
            contentLength = responseBody.contentLength()
        }
        return contentLength
    }

    override fun source(): BufferedSource {
        if (callback == null) {
            return responseBody.source()
        }
        if (progressSource == null) {
            progressSource = source(responseBody.source()).buffer()
        }
        return progressSource!!
    }

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            var totalBytesRead = 0L
            var lastBytesRead = 0L

            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                lastBytesRead = totalBytesRead
                totalBytesRead += if (bytesRead != -1L) bytesRead else 0
                if (totalBytesRead != lastBytesRead) {
                    callback?.onProgress(totalBytesRead, contentLength())
                }
                return bytesRead
            }
        }
    }

    override fun close() {
        try {
            progressSource?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}