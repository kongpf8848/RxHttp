package com.github.kongpf8848.rxhttp.request;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.trello.rxlifecycle2.LifecycleTransformer;

import okhttp3.RequestBody;

public class DownloadRequest extends AbsRequest {

    private String dir;

    public DownloadRequest(Context context) {
        super(context);
    }

    public DownloadRequest(Activity activity) {
        super(activity);
    }
    public DownloadRequest(Fragment fragment) {
        super(fragment);
    }
    public DownloadRequest(LifecycleTransformer transformer) {
        super(transformer);
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
