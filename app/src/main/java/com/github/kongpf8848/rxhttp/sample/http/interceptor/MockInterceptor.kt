package com.github.kongpf8848.rxhttp.sample.http.interceptor

import com.github.kongpf8848.rxhttp.sample.http.TKURL
import com.github.kongpf8848.rxhttp.sample.utils.MockUtils
import okhttp3.*
import java.io.IOException
import java.net.HttpURLConnection

class MockInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response: Response? = null
        val responseBuilder = Response.Builder()
                .code(HttpURLConnection.HTTP_OK)
                .message("")
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .addHeader("Content-Type", "application/json")
        val url = request.url().toString()

        response = if (TKURL.URL_GET.equals(url)) {
            /**
             * 模拟GET请求
             */
            val responseString = MockUtils.getBannerData()
            responseBuilder.body(ResponseBody.create(MediaType.parse("application/json"), responseString.toByteArray()))
            responseBuilder.build()
        } else if (TKURL.URL_POST.equals(url) || TKURL.URL_PUT.equals(url) || TKURL.URL_DELETE.equals(url)) {
            /**
             * 模拟POST请求
             */
            val responseString = MockUtils.getUserData()
            responseBuilder.body(ResponseBody.create(MediaType.parse("application/json"), responseString.toByteArray()))
            responseBuilder.build()
        } else {
            chain.proceed(request)
        }

        return response
    }
}