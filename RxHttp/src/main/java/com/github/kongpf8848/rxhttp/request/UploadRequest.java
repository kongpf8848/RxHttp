package com.github.kongpf8848.rxhttp.request;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.github.kongpf8848.rxhttp.HttpConstants;
import com.github.kongpf8848.rxhttp.Platform;
import com.github.kongpf8848.rxhttp.ProgressRequestBody;
import com.github.kongpf8848.rxhttp.callback.ProgressCallback;
import com.github.kongpf8848.rxhttp.callback.UploadCallback;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadRequest extends PostRequest {
    public UploadRequest(Context context) {
        super(context);
    }

    public UploadRequest(Activity activity) {
        super(activity);
    }
    public UploadRequest(Fragment fragment) {
        super(fragment);
    }
    public UploadRequest(LifecycleTransformer transformer) {
        super(transformer);
    }
    @Override
    public RequestBody buildRequestBody() {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof File) {
                File file = (File) value;
                builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse(HttpConstants.MIME_TYPE_BINARY), file));
            } else {
                builder.addFormDataPart(key, String.valueOf(value));
            }
        }
        return new ProgressRequestBody(builder.build(), new ProgressCallback() {
            @Override
            public void onProgress(final long totalBytes, final long readBytes) {
                Platform.get().defaultCallbackExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        if(callback instanceof UploadCallback) {
                            ((UploadCallback) callback).onProgress(totalBytes,readBytes);
                        }
                    }
                });
            }
        });

    }
}
