package com.github.kongpf8848.rxhttp;

import android.util.Log;

import com.github.kongpf8848.rxhttp.callback.HttpCallback;

import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.EndConsumerHelper;

public class HttpObserver<T> implements Observer<T>,Disposable {

    private static final String TAG = "HttpObserver";

    private HttpCallback callback;
    private Object tag;

    final AtomicReference<Disposable> upstream = new AtomicReference<Disposable>();


    public HttpObserver(HttpCallback callback,Object tag) {
        this.callback = callback;
        this.tag=tag;
    }

    @Override
    public final void onSubscribe(@NonNull Disposable d) {
        Log.d(TAG, "onSubscribe() called with: d = [" + d.getClass().getName() + "]");
        if (EndConsumerHelper.setOnce(this.upstream, d, getClass())) {
            if(tag!=null) {
                RxHttpTagManager.getInstance().addTag(tag,d);
            }
            onStart();
        }
    }
    @Override
    public final boolean isDisposed() {
        return upstream.get() == DisposableHelper.DISPOSED;
    }

    @Override
    public final void dispose() {
        if(tag!=null) {
            RxHttpTagManager.getInstance().removeTag(tag);
        }
        DisposableHelper.dispose(upstream);
    }

    private void onStart() {
        if (this.callback != null) {
            callback.onStart();
        }
    }

    @Override
    public void onNext(T response) {
        if (this.callback != null) {
            callback.onNext(response);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (this.callback != null) {
            callback.onError(e);
        }
        dispose();
    }

    @Override
    public void onComplete() {
        if (this.callback != null) {
            callback.onComplete();
        }
        dispose();
    }



}
