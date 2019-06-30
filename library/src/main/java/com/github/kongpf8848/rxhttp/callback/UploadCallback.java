package com.github.kongpf8848.rxhttp.callback;


import com.github.kongpf8848.rxhttp.bean.UploadInfo;

public abstract class UploadCallback<T> extends HttpCallback<T> {

    public abstract void onProgress(UploadInfo uploadInfo);

}
