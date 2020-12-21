package com.github.kongpf8848.rxhttp.exception

class RxHttpException(val code: Int,  override val message: String?) : Exception(message) {

    override fun toString(): String {
        return "RxHttpException[code=$code,message=$message]"
    }

    companion object {
        const val RXHTTP_CODE_OK = 800
        const val RXHTTP_CODE_EXCEPTION = RXHTTP_CODE_OK + 1
        const val RXHTTP_CODE_RESPONSE_DATA_EMPTY_EXCEPTION = RXHTTP_CODE_EXCEPTION + 2
        const val RXHTTP_CODE_JSON_PARSE_EXCEPTION = RXHTTP_CODE_EXCEPTION + 3
        const val RXHTTP_CODE_RESPONSE_NULL_EXCEPTION = RXHTTP_CODE_EXCEPTION + 4
        const val RXHTTP_CODE_CONNECTION_EXCEPTION = RXHTTP_CODE_EXCEPTION + 5
    }

}