package com.github.kongpf8848.rxhttp.request;

import okhttp3.RequestBody;

public class PostFormRequest extends AbsRequest {

    public PostFormRequest(String url) {
        super(url);
    }

    @Override
    public RequestBody buildRequestBody() {
        return null;
    }
}
