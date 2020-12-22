package com.github.kongpf8848.rxhttp.util

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets

/**
 * Created by pengf on 2017/2/21.
 */
object StreamUtil {

    //将InputStream转换为String
    fun toString(`is`: InputStream): String? {
        return try {
            val byteStream = toByte(`is`)
            String(byteStream, StandardCharsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    //将InputStream转换为字节数组
    @Throws(IOException::class)
    fun toByte(`is`: InputStream): ByteArray {
        val baos = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var len = -1
        while (`is`.read(buffer).also { len = it } != -1) {
            baos.write(buffer, 0, len)
        }
        baos.flush()
        baos.close()
        `is`.close()
        return baos.toByteArray()
    }
}