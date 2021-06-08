package com.jsy.tk.library.http.exception

/**
 * 服务器返回的异常
 */
class ServerException(val code: Int, val msg: String?) : RuntimeException(msg)