package com.github.kongpf8848.rxhttp

import io.reactivex.disposables.Disposable
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class RxHttpTagManager private constructor(){

    private object RxHttpTagManager {
        val holder = RxHttpTagManager()
    }

    private val map: MutableMap<Any, Disposable> = ConcurrentHashMap(16)

    fun addTag(tag: Any?, disposable: Disposable) {
        if (tag != null) {
            this.map[tag] = disposable
        }
    }

    fun removeTag(tag: Any?) {
        if (tag != null) {
            map.remove(tag)
        }
    }

    fun cancelTag(tag: Any?) {
        if (tag != null) {
            if (map.containsKey(tag)) {
                dispose(map[tag])
                removeTag(tag)
            }
        } else {
            for ((_, value) in map) {
                dispose(value)
            }
            map.clear()
        }
    }

    private fun dispose(disposable: Disposable?) {
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
        }
    }

    companion object {
        fun getInstance()=RxHttpTagManager.holder

        fun generateRandomTag(): String {
            return UUID.randomUUID().toString()
        }
    }
}