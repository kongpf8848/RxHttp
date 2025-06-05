package io.github.kongpf8848.rxhttp.sample.mvvm

import android.content.Context
import android.util.Log
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

interface IBaseMvvm {

    fun findType(type: Type): Type? {
        return when (type) {
            is ParameterizedType -> type
            is Class<*> -> {
                findType(type.genericSuperclass)
            }

            else -> {
                null
            }
        }
    }

    fun getLayoutId(context: Context, type: Type): Int {
        val name = type.toString()
            .substringAfterLast('.')               // 提取类名（如 "MainActivityBinding"）
            .removeSuffix("Binding")               // 移除结尾的 Binding
            .replace(Regex("(?<=.)([A-Z])"), "_$1")// 驼峰转下划线（如 "Main_Activity"）
            .lowercase()
        Log.d("JACK88", "name:${name},type:$type")
        return context.resources.getIdentifier(name, "layout", context.packageName)

    }

}