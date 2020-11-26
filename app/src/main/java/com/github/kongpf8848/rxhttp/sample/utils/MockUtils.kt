package com.github.kongpf8848.rxhttp.sample.utils

import com.github.kongpf8848.rxhttp.sample.bean.Banner
import com.jsy.tk.library.http.TKResponse

object MockUtils {

    /**
     * 获取轮播图数据
     */
    fun getBannerData():String {
        val list= listOf(
                Banner(1, "http://t8.baidu.com/it/u=198337120,441348595&fm=79&app=86&f=JPEG?w=1280&h=732", "https://www.baidu.com", "我是轮播图_1"),
                Banner(2, "http://t8.baidu.com/it/u=1484500186,1503043093&fm=79&app=86&f=JPEG?w=1280&h=853", "https://www.qq.com", "我是轮播图_2")
        )
        val response=TKResponse(200,"",list)
        return GsonUtils.toJson(response)

    }
}