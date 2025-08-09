package io.github.kongpf8848.rxhttp.interceptors

import io.github.kongpf8848.rxhttp.curl.CurlCommandParser
import okhttp3.Interceptor
import okhttp3.Response

class CurlLoggingInterceptor(var tag: String? = null) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        CurlCommandParser(tag).parse(request)
        return chain.proceed(request)
    }


}
