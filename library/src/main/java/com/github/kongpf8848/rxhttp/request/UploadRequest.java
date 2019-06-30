package com.github.kongpf8848.rxhttp.request;


import com.github.kongpf8848.rxhttp.HttpConstants;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadRequest extends AbsRequest {

    public UploadRequest(String url) {
        super(url);
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
        return builder.build();

    }
}
