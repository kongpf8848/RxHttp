package io.github.kongpf8848.rxhttp.sample.mvc

import io.github.kongpf8848.rxhttp.sample.http.TKResponse
import io.github.kongpf8848.rxhttp.typebuilder.TypeBuilder
import io.github.kongpf8848.rxhttp.util.TypeUtil
import java.lang.reflect.Type

abstract class MVCHttpCallback<T> {

    private val type: Type

    init {
        val arg = TypeUtil.getType(javaClass)
        type = TypeBuilder
                .newInstance(TKResponse::class.java)
                .addTypeParam(arg)
                .build()
    }

    fun getType(): Type {
        return this.type
    }

    /**
     * 请求开始时回调，可以在此加载loading对话框等,默认为空实现
     */
    open fun onStart() {}
    /**
     * 抽象方法，请求成功回调，返回内容为对应的数据模型
     */
    abstract fun onSuccess(result: T?)

    /**
     * 抽象方法，请求失败回调，返回内容为code(错误码)，msg(错误信息)
     */
    abstract fun onFailure(code: Int, msg: String?)

    /**
     * 上传进度回调，默认为空实现
     */
    open fun onProgress(readBytes: Long, totalBytes: Long) {}

    /**
     * 请求完成时回调，请求成功之后才会回调此方法，默认为空实现
     */
    open fun onComplete() {}

}