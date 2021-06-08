package io.github.kongpf8848.rxhttp.sample.http.exception

class NullResponseException(val code: Int, val msg: String?) : RuntimeException(msg)