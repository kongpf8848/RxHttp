package com.github.kongpf8848.rxhttp.callback;

public interface ProgressCallback {
    void onProgress(long readBytes,long totalBytes);
}
