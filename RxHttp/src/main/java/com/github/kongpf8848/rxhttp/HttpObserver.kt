package com.github.kongpf8848.rxhttp

import com.github.kongpf8848.rxhttp.callback.HttpCallback
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableHelper
import io.reactivex.internal.util.EndConsumerHelper
import java.util.concurrent.atomic.AtomicReference

class HttpObserver<T>(private val callback: HttpCallback<T>?, private val tag: Any?) : Observer<T>, Disposable {
    val upstream = AtomicReference<Disposable>()
    override fun onSubscribe(d: Disposable) {
        if (EndConsumerHelper.setOnce(upstream, d, javaClass)) {
            if (tag != null) {
                RxHttpTagManager.getInstance().addTag(tag, d)
            }
            onStart()
        }
    }

    override fun isDisposed(): Boolean {
        return upstream.get() === DisposableHelper.DISPOSED
    }

    override fun dispose() {
        if (tag != null) {
            RxHttpTagManager.getInstance().removeTag(tag)
        }
        DisposableHelper.dispose(upstream)
    }

    private fun onStart() {
        callback?.onStart()
    }

    override fun onNext(response: T) {
        callback?.onNext(response)
    }

    override fun onError(e: Throwable) {
        callback?.onError(e)
        dispose()
    }

    override fun onComplete() {
        callback?.onComplete()
        dispose()
    }

    companion object {
        private const val TAG = "HttpObserver"
    }

}