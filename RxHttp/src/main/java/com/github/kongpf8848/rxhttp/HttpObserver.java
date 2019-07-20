package com.github.kongpf8848.rxhttp;

import com.github.kongpf8848.rxhttp.callback.HttpCallback;


import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class HttpObserver<T> extends DisposableObserver<T> {

    private HttpCallback callback;

    public HttpObserver(HttpCallback callback) {
        this.callback = callback;
    }


    @Override
    protected void onStart() {
        if (this.callback != null) {
            callback.onStart();
        }
    }

    @Override
    public void onNext(T response) {
        if (this.callback != null) {
            callback.onResponse(response);
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
