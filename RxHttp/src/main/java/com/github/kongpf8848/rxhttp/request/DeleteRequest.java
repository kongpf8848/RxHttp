package com.github.kongpf8848.rxhttp.request;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import okhttp3.RequestBody;

public class DeleteRequest extends AbsRequest {


    public DeleteRequest(Context context) {
        super(context);
    }

    public DeleteRequest(Fragment fragment) {
        super(fragment);
    }

    public DeleteRequest(Activity activity) {
        super(activity);
    }

    @Override
    protected RequestBody buildRequestBody() {
        return null;
    }
}
