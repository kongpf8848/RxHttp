package io.github.kongpf8848.rxhttp.curl

import android.util.Log

object CurlPrinter {

    private const val SINGLE_DIVIDER = "────────────────────────────────────────────"

    private const val DEFAULT_TAG = "CURL"

    fun print(tag: String?, url: String, msg: String?) {
        val printTag = tag ?: DEFAULT_TAG
        val logMsg = StringBuilder("\n")
        logMsg.append("\n")
        logMsg.append("URL: $url")
        logMsg.append("\n")
        logMsg.append(SINGLE_DIVIDER)
        logMsg.append("\n")
        logMsg.append(msg)
        logMsg.append("\n")
        logMsg.append(SINGLE_DIVIDER)
        logMsg.append("\n")
        Log.d(printTag, logMsg.toString())
    }

}