package com.github.kongpf8848.rxhttp.request;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.trello.rxlifecycle2.LifecycleTransformer;

public class PutRequest extends PostRequest {

    public PutRequest(Context context) {
        super(context);
    }

    public PutRequest(Activity activity) {
        super(activity);
    }

    public PutRequest(Fragment fragment) {
        super(fragment);
    }

    public PutRequest(LifecycleTransformer transformer) {
        super(transformer);
    }
}
