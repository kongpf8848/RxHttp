package com.github.kongpf8848.rxhttp.request;

import okhttp3.RequestBody;

public class DownloadRequest extends AbsRequest {

    private String dir;

    public DownloadRequest(String url) {
        super(url);
    }

    public DownloadRequest dir(String dir) {
        this.dir = dir;
        return this;
    }

    public String getDir() {
        return this.dir;
    }

    @Override
    protected RequestBody buildRequestBody() {
        return null;
    }
}
