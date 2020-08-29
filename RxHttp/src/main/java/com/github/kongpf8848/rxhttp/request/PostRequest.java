package com.github.kongpf8848.rxhttp.request;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;

import com.github.kongpf8848.rxhttp.HttpConstants;
import com.trello.rxlifecycle2.LifecycleTransformer;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PostRequest extends AbsRequest {
    private String content;
    private String type;
    public PostRequest(Context context) {
        super(context);
    }
    public PostRequest(Activity activity) {
        super(activity);
    }
    public PostRequest(Fragment fragment) {
        super(fragment);
    }
    public PostRequest(LifecycleTransformer transformer) {
        super(transformer);
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
            return RequestBody.create(MediaType.parse(HttpConstants.MIME_TYPE_JSON), content);
        }
        else{
            return RequestBody.create(MediaType.parse(type), content);
        }
    }
}
