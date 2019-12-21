package com.github.kongpf8848.rxhttp.request;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.trello.rxlifecycle2.LifecycleTransformer;

import okhttp3.RequestBody;

public class DownloadRequest extends AbsRequest {

    private String dir;
    private String filename;
    private String md5;
    private boolean breakpoint;

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

    public DownloadRequest filename(String filename) {
        this.filename = filename;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public DownloadRequest breakpoint(boolean breakpoint) {
        this.breakpoint = breakpoint;
        return this;
    }

    public boolean isBreakpoint() {
        return breakpoint;
    }

    public DownloadRequest md5(String md5) {
        this.md5 = md5;
        return this;
    }

    public String getMd5() {
        return md5;
    }

    @Override
    protected RequestBody buildRequestBody() {
        return null;
    }
}
