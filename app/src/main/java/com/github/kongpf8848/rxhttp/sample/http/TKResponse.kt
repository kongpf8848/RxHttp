package com.jsy.tk.library.http

import java.io.Serializable

class TKResponse<T>(val code:Int,val msg: String?, val data: T?) : Serializable {
    companion object{
        const val STATUS_OK=200
    }
    fun isSuccess():Boolean{
        return code==STATUS_OK
    }

}
