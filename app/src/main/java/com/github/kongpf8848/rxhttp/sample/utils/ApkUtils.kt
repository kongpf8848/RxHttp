package com.github.kongpf8848.rxhttp.sample.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File

object ApkUtils {

    /**
     * 安装Apk
     */
    fun installApk(context: Context, file: File?, authority: String) {
        if (file == null || !file.exists()) {
            return
        }
        try {
//            val pm = context.packageManager
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                if (!pm.canRequestPackageInstalls()) {
//                    val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + context.packageName))
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    context.startActivity(intent)
//                    return
//                }
//            }
            val intent = Intent(Intent.ACTION_VIEW)
            val uri: Uri
            uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                FileProvider.getUriForFile(context, authority, file)
            } else {
                Uri.fromFile(file)
            }
            intent.setDataAndType(uri, "application/vnd.android.package-archive")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
