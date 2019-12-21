package com.github.kongpf8848.rxhttp.callback;

 interface BaseCallback<T> {
    /**
     * http请求开始时回调
     */
    void onStart();
    /**
     * http请求成功时回调
     */
    void onNext(T response);
    /**
     * http请求失败时回调
     */
    void onError(Throwable e);
    /**
     * http请求完成时回调
     */
    void onComplete();
}
