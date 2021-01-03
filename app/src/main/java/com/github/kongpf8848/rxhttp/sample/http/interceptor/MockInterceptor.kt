package com.github.kongpf8848.rxhttp.sample.http.interceptor

import com.github.kongpf8848.rxhttp.HttpConstants
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
                .addHeader("Content-Type", HttpConstants.MIME_TYPE_JSON)
        val url = request.url().toString()

        /**
         * 模拟网络请求
         */
        response = if (url.startsWith(TKURL.URL_GET)) {
            val responseString = MockUtils.getBannerData()
            responseBuilder.body(ResponseBody.create(MediaType.parse(HttpConstants.MIME_TYPE_JSON), responseString.toByteArray()))
            responseBuilder.build()
        } else if (TKURL.URL_POST == url || TKURL.URL_POST_FORM == url || TKURL.URL_PUT == url || TKURL.URL_DELETE == url ) {
            val responseString = MockUtils.getUserData()
            responseBuilder.body(ResponseBody.create(MediaType.parse(HttpConstants.MIME_TYPE_JSON), responseString.toByteArray()))
            responseBuilder.build()
        } else {
            chain.proceed(request)
        }

        return response
    }
}