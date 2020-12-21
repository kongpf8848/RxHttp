package com.github.kongpf8848.rxhttp

import android.os.Build
import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

open class Platform {
    open fun defaultCallbackExecutor(): Executor? {
        return null
    }

    internal class Java : Platform() {
        override fun defaultCallbackExecutor(): Executor? {
            return Executor { command -> Executors.newCachedThreadPool().execute(command) }
        }
    }

    internal class Android : Platform() {
        override fun defaultCallbackExecutor(): Executor? {
            return MainThreadExecutor()
        }

        internal class MainThreadExecutor : Executor {
            private val handler = Handler(Looper.getMainLooper())
            override fun execute(r: Runnable) {
                handler.post(r)
            }
        }
    }

    companion object {
        private val PLATFORM = findPlatform()
        fun get(): Platform {
            return PLATFORM
        }

        private fun findPlatform(): Platform {
            try {
                Class.forName("android.os.Build")
                if (Build.VERSION.SDK_INT != 0) {
                    return Android()
                }
            } catch (ignored: ClassNotFoundException) {
            }
            return Java()
        }
    }
}