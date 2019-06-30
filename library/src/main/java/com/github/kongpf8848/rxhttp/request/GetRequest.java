package com.github.kongpf8848.rxhttp.request;

import okhttp3.RequestBody;

public class GetRequest extends AbsRequest {

    public GetRequest(String url) {
        super(url);
    }

    @Override
    public RequestBody buildRequestBody() {
        return null;
    }

}
