package io.github.kongpf8848.rxhttp.sample.utils

import android.content.Context
import java.io.*

object AssetUtils {

    /**
     * 读取assets目录下的文件
     */
    fun openFile(context: Context,fileName: String):InputStream?{
        try{
           return context.assets.open(fileName)
        }
        catch (e:IOException){
            e.printStackTrace()
        }
        return null
    }

    /**
     * 复制src/main/assets目录下的文件到指定目录
     */
    fun copyAssets(context: Context, fileName: String, destDirectory: String): Boolean {
        var destDir = destDirectory
        var `in`: InputStream? = null
        var out: OutputStream? = null
        try {
            `in` = context.resources.assets.open(fileName)
            if (!destDir.endsWith(File.separator)) destDir += File.separator
            val parent = File(destDir)
            if (!parent.exists()) {
                parent.mkdirs()
            }
            out = FileOutputStream(destDir + fileName)
            val buffer = ByteArray(1024)
            var length: Int
            while (`in`.read(buffer).also { length = it } > 0) {
                out.write(buffer, 0, length)
            }
            out.flush()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
            try {
                `in`?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {
                out?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return true
    }
}