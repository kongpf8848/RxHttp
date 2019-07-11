package com.github.kongpf8848.rxhttp.request;

import android.text.TextUtils;

import com.github.kongpf8848.rxhttp.HttpConstants;


import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PostRequest extends AbsRequest {
    private String content;
    private String type;
    public PostRequest(String url) {
        super(url);
    }

    public PostRequest content(String content){
        this.content=content;
        return this;
    }
    public PostRequest type(String type){
        this.type=type;
        return this;
    }

    @Override
    public RequestBody buildRequestBody() {
        if(TextUtils.isEmpty(type)) {
            return RequestBody.create(MediaType.parse(HttpConstants.MIME_TYPE_TEXT), content);
        }
        else{
            return RequestBody.create(MediaType.parse(type), content);
        }
    }
}
