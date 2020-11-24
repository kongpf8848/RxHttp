package com.github.kongpf8848.rxhttp.request;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;


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

}
