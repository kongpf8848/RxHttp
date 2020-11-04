package com.github.kongpf8848.rxhttp.callback;

import com.github.kongpf8848.rxhttp.bean.DownloadInfo;

public abstract class DownloadCallback extends HttpCallback<DownloadInfo> {
    public abstract void onProgress(DownloadInfo downloadInfo);
}
