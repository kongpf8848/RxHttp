package com.github.kongpf8848.rxhttp.request;


import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import androidx.fragment.app.Fragment;

import com.github.kongpf8848.rxhttp.ByteArrayRequestBody;
import com.github.kongpf8848.rxhttp.HttpConstants;
import com.github.kongpf8848.rxhttp.Platform;
import com.github.kongpf8848.rxhttp.ProgressRequestBody;
import com.github.kongpf8848.rxhttp.callback.ProgressCallback;
import com.github.kongpf8848.rxhttp.util.StreamUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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


    @Override
    public RequestBody buildRequestBody() {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        Map<String, Object> params=getParams();
        if(params!=null && params.size()>0) {
            Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof File) {
                    File file = (File) value;
                    builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse(HttpConstants.MIME_TYPE_BINARY), file));
                } else if (value instanceof Uri) {
                    try {
                        InputStream inputStream = getActualContext().getContentResolver().openInputStream((Uri) value);
                        if (inputStream != null) {
                            byte[] bytes = StreamUtil.toByte(inputStream);
                            builder.addFormDataPart(key, key, new ByteArrayRequestBody(MediaType.parse(HttpConstants.MIME_TYPE_BINARY), bytes));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    builder.addFormDataPart(key, String.valueOf(value));
                }
            }
        }
        return new ProgressRequestBody(builder.build(), new ProgressCallback() {
            @Override
            public void onProgress(final long readBytes,final long totalBytes) {
                Platform.get().defaultCallbackExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                         callback.onProgress(readBytes,totalBytes);
                    }
                });
            }
        });

    }
}
