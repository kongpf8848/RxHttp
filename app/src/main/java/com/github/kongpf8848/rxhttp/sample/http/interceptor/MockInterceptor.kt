package com.github.kongpf8848.rxhttp.sample.http.interceptor

import com.github.kongpf8848.rxhttp.HttpConstants
import com.github.kongpf8848.rxhttp.sample.http.TKURL
import com.github.kongpf8848.rxhttp.sample.utils.MockUtils
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import java.net.HttpURLConnection

class MockInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.toString()
        val responseBuilder = Response.Builder()
                .code(HttpURLConnection.HTTP_OK)
                .message("")
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .addHeader("Content-Type", HttpConstants.MIME_TYPE_JSON)
        var responseString=""
        return if (url.startsWith(TKURL.URL_GET)) {
            responseString = MockUtils.getBannerData()
            responseBuilder.body(ResponseBody.create(HttpConstants.MIME_TYPE_JSON.toMediaTypeOrNull(), responseString.toByteArray()))
            responseBuilder.build()

        } else if ( TKURL.URL_POST == url || TKURL.URL_POST_FORM == url || TKURL.URL_PUT == url || TKURL.URL_DELETE == url) {
            responseString = MockUtils.getUserData()
            responseBuilder.body(ResponseBody.create(HttpConstants.MIME_TYPE_JSON.toMediaTypeOrNull(), responseString.toByteArray()))
            responseBuilder.build()
        }
        else {
            chain.proceed(request)
        }
    }
}