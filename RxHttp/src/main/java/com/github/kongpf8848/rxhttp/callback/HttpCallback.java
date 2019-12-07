package com.github.kongpf8848.rxhttp.callback;

import android.util.Log;

import com.github.kongpf8848.rxhttp.util.TypeUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public abstract class HttpCallback<T> implements BaseCallback<T> {
    private Type type;

    public HttpCallback() {
        this.type = TypeUtil.getType(getClass());
    }

    public HttpCallback(Type type){
        this.type=type;
    }

    public Type getType() {
        return type;
    }

}
