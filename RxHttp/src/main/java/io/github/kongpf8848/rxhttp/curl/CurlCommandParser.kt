package io.github.kongpf8848.rxhttp.curl

import android.util.Log
import io.github.kongpf8848.rxhttp.HttpConstants
import okhttp3.Headers
import okhttp3.Request
import okio.Buffer
import java.nio.charset.StandardCharsets.UTF_8


class CurlCommandParser(var tag: String? = null) {
    private val curlCommandBuilder = StringBuilder();
    fun parse(request: Request) {
        curlCommandBuilder.append("curl")
        curlCommandBuilder.append(" -X ${request.method}")
        for ((key, value) in request.headers) {
            addHeader(key, value)
        }

        val requestBody = request.body
        if (requestBody != null) {
            val contentType = requestBody.contentType()
            if (contentType != null) {
                addHeader("Content-Type", contentType.toString())
            }
            if (bodyHasUnknownEncoding(request.headers)) {
            } else if (requestBody.isDuplex()) {
            } else if (requestBody.isOneShot()) {
            } else {
                val buffer = Buffer()
                requestBody.writeTo(buffer)
                val charset = contentType?.charset(UTF_8) ?: UTF_8
                curlCommandBuilder.append(" -d '" + buffer.readString(charset) + "'");
            }
        }
        curlCommandBuilder.append(" \"${request.url}\"")
        CurlPrinter.print(tag,"${request.url}", curlCommandBuilder.toString());

    }

    private fun bodyHasUnknownEncoding(headers: Headers): Boolean {
        val contentType=headers["Content-Type"]
        if (contentType!=null) {
            return !(contentType.contains(HttpConstants.TEXT_PLAIN) || contentType.contains(
                HttpConstants.APPLICATION_JSON
            ) || contentType.contains(HttpConstants.APPLICATION_XML))
        }
        val contentEncoding = headers["Content-Encoding"] ?: return false
        return !contentEncoding.equals("identity", ignoreCase = true) &&
                !contentEncoding.equals("gzip", ignoreCase = true)
    }

    private fun addHeader(name: String, value: String) {
        curlCommandBuilder.append(" -H \"$name: $value\"")
    }
}