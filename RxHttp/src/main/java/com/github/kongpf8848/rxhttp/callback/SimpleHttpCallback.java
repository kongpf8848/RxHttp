package com.github.kongpf8848.rxhttp.callback;

import java.lang.reflect.Type;

public class SimpleHttpCallback<T> extends HttpCallback<T> {

    public SimpleHttpCallback() {
        super();
    }

    public SimpleHttpCallback(Type type) {
        super(type);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onNext(T response) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onProgress(long readBytes, long totalBytes) {

    }
}
