
package com.github.kongpf8848.rxhttp.sample.utils

import android.content.Context
import android.text.TextUtils
import android.util.Log

object LogUtils {
    var TAG_DEFAULT = "TKLOG"
    var debug = false
    private var context: Context? = null

    fun init(ctx: Context, isDebug: Boolean) {
        context = ctx.applicationContext
        debug = isDebug
    }

    fun v(msg: String?) {
        if (!debug) {
            return
        }
        v(TAG_DEFAULT, msg)
    }

    fun v(classs: Class<*>, msg: String) {
        if (!debug) {
            return
        }
        v(classs.simpleName, classs.simpleName + "==" + msg)
    }

    fun v(tag: String, msg: String?) {
        if (!debug) {
            return
        }
        log(Log.VERBOSE, tag, msg)
    }

    fun d(msg: String?) {
        if (!debug) {
            return
        }
        d(TAG_DEFAULT, msg)
    }

    fun d(classs: Class<*>, msg: String) {
        if (!debug) {
            return
        }
        d(classs.simpleName, classs.simpleName + "==" + msg)
    }

    fun d(tag: String, msg: String?) {
        if (!debug) {
            return
        }
        log(Log.DEBUG, tag, msg)
    }

    fun i(msg: String?) {
        if (!debug) {
            return
        }
        i(TAG_DEFAULT, msg)
    }

    fun i(classs: Class<*>, msg: String) {
        if (!debug) {
            return
        }
        i(classs.simpleName, classs.simpleName + "==" + msg)
    }

    fun i(tag: String, msg: String?) {
        if (!debug) {
            return
        }
        log(Log.INFO, tag, msg)
    }

    fun w(msg: String?) {
        if (!debug) {
            return
        }
        w(TAG_DEFAULT, msg)
    }

    fun w(classs: Class<*>, msg: String) {
        if (!debug) {
            return
        }
        w(classs.simpleName, classs.simpleName + "==" + msg)
    }

    fun w(tag: String, msg: String?) {
        if (!debug) {
            return
        }
        log(Log.WARN, tag, msg)
    }

    fun e(msg: String?) {
        if (!debug) {
            return
        }
        e(TAG_DEFAULT, msg)
    }

    fun e(classs: Class<*>, msg: String) {
        if (!debug) {
            return
        }
        e(classs.simpleName, classs.simpleName + "==" + msg)
    }

    fun e(tag: String, msg: String?) {
        if (!debug) {
            return
        }
        log(Log.ERROR, tag, msg)
    }

    private fun log(level: Int, tag: String, msg: String?) {
        if (TextUtils.isEmpty(msg)) {
            return
        }
        if (level == Log.VERBOSE) {
            Log.v(tag, msg!!)
        } else if (level == Log.DEBUG) {
            Log.d(tag, msg!!)
        } else if (level == Log.INFO) {
            Log.i(tag, msg!!)
        } else if (level == Log.WARN) {
            Log.w(tag, msg!!)
        } else if (level == Log.ERROR) {
            Log.e(tag, msg!!)
        }
    }
}