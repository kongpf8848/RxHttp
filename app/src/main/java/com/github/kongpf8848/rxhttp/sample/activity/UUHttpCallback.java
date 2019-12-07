package com.github.kongpf8848.rxhttp.sample.activity;

import com.github.kongpf8848.rxhttp.sample.bean.BaseResponse;
import com.github.kongpf8848.rxhttp.util.TypeUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public abstract class UUHttpCallback<T> {
    private Type type;
    public UUHttpCallback() {
        this.type=new TypeToken<BaseResponse<T>>(){}.getType();
        this.type = TypeUtil.getType(getClass());
    }

    public Type getType(){
        return this.type;
    }

    abstract void onResponse(T result);
}