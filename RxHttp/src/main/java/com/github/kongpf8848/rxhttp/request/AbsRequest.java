package com.github.kongpf8848.rxhttp.request;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.github.kongpf8848.rxhttp.RxHttp;
import com.github.kongpf8848.rxhttp.callback.HttpCallback;
import com.trello.rxlifecycle2.LifecycleTransformer;


import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;

public abstract class AbsRequest<T> {

    protected Object context;
    protected String url;
    protected Object tag;
    protected Map<String,String> headers=new HashMap<>();
    protected Map<String,Object> params=new HashMap<>();
    protected HttpCallback<T> callback;

    protected abstract RequestBody buildRequestBody();

    AbsRequest(LifecycleTransformer transformer){
        this.context=transformer;
    }
    AbsRequest(Context context){
        this.context=context;
    }
    AbsRequest(Fragment fragment){
        this.context=fragment;
    }
    AbsRequest(Activity activity){
        this.context=activity;
    }

    public AbsRequest url(String url){
        this.url=url;
        return this;
    }
    public AbsRequest tag(Object tag){
        this.tag=tag;
        return this;
    }
    public AbsRequest header(String key, String value){
        this.headers.put(key,value);
        return this;
    }
    public AbsRequest headers(Map<String,String>headers){
        if(headers!=null) {
            this.headers = headers;
        }
        return this;
    }
    public AbsRequest param(String key, Object value){
        this.params.put(key,value);
        return this;
    }
    public AbsRequest params(Map<String,Object>params){
        if(params!=null){
            this.params=params;
        }
        return this;
    }

    public Object getContext() {
        return context;
    }
    public String getUrl() {
        return url;
    }

    public Object getTag() {
        return tag;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
    public Map<String, Object> getParams() {
        return params;
    }

    public void enqueue(HttpCallback<T> callback)
    {
        this.callback=callback;
        RxHttp.getInstance().enqueue(this,callback);
    }
}

