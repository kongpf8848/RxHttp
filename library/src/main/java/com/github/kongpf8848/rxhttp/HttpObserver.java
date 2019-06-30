package com.github.kongpf8848.rxhttp;

import com.github.kongpf8848.rxhttp.callback.HttpCallback;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class HttpObserver<T> implements Observer<T> {

    private Disposable disposable;
    private HttpCallback callback;

    public HttpObserver(HttpCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
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
        onDispose();
        if (this.callback != null) {
            callback.onError(e);
        }
    }

    @Override
    public void onComplete() {
        onDispose();
        if (this.callback != null) {
            callback.onComplete();
        }
    }

    private void onDispose(){
        if(disposable!=null){
            if(!disposable.isDisposed()){
                disposable.dispose();
            }
        }
    }
}
