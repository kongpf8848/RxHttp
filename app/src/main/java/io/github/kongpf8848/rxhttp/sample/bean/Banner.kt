package io.github.kongpf8848.rxhttp.sample.bean

import java.io.Serializable

data class Banner(
        var id: Int,
        var pic: String,
        var url: String,
        var title: String? = null
) : Serializable
