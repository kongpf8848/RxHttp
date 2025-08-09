package io.github.kongpf8848.rxhttp.interceptors

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.Response
import okio.BufferedSink
import okio.GzipSink
import okio.buffer

class GzipRequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        if (originRequest.body == null || originRequest.header("Content-Encoding") != null) {
            return chain.proceed(originRequest);
        }
        val newRequest = originRequest.newBuilder().header("Content-Encoding", "gzip")
            .method(originRequest.method, gzip(originRequest.body!!))
            .build()
        return chain.proceed(newRequest)
    }

    private fun gzip(body: RequestBody) = object : RequestBody() {

        override fun contentType(): MediaType? {
            return body.contentType()
        }

        override fun contentLength(): Long {
            return -1
        }

        override fun writeTo(sink: BufferedSink) {
            val gzipSink = GzipSink(sink).buffer()
            body.writeTo(gzipSink)
            gzipSink.close()
        }

    }
}
