package com.github.kongpf8848.rxhttp.request;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.trello.rxlifecycle2.LifecycleTransformer;

import okhttp3.RequestBody;

public class PostFormRequest extends AbsRequest {

    public PostFormRequest(Context context) {
        super(context);
    }

    public PostFormRequest(Activity activity) {
        super(activity);
    }
    public PostFormRequest(Fragment fragment) {
        super(fragment);
    }
    public PostFormRequest(LifecycleTransformer transformer) {
        super(transformer);
    }

    @Override
    public RequestBody buildRequestBody() {
        return null;
    }
}
