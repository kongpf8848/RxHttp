package com.github.kongpf8848.rxhttp

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

class RetryWithDelay(private val maxRetries: Int, private val retryDelayMillis: Long) : Function<Observable<Throwable?>, ObservableSource<*>> {
    private var retryCount = 0

    @Throws(Exception::class)
    override fun apply(throwableObservable: Observable<Throwable?>): ObservableSource<*> {
        return throwableObservable.flatMap(object : Function<Throwable?, ObservableSource<*>> {
            @Throws(Exception::class)
            override fun apply(throwable: Throwable): ObservableSource<*> {
                return if (++retryCount <= maxRetries) {
                    Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS)
                } else Observable.error<Any>(throwable)
            }

        })
    }

}