package io.github.kongpf8848.rxhttp.sample.bean

import java.io.Serializable

data class Feed(
        var date: String,
        var stories: List<Story>? = null,
        var top_stories: List<TopStory>? = null
) : Serializable

data class Story(
        var id: Long = 0L,
        var type: Int = 0,
        var title: String? = null,
        var url: String? = null,
        var images: List<String>? = null,
        var hint: String? = null,
        var ga_prefix: String? = null,
        var image_hue: String? = null
) : Serializable

data class TopStory(
        var id: Long = 0L,
        var type: Int = 0,
        var title: String? = null,
        var url: String? = null,
        var image: String? = null,
        var hint: String? = null,
        var ga_prefix: String? = null,
        var image_hue: String? = null
) : Serializable