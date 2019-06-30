package com.github.kongpf8848.rxhttp.request;

import com.github.kongpf8848.rxhttp.RxHttp;
import com.github.kongpf8848.rxhttp.callback.HttpCallback;


import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;

public abstract class AbsRequest {

    protected String url;
    protected Object tag;
    protected Map<String,String> headers=new HashMap<>();
    protected Map<String,Object> params=new HashMap<>();

    protected abstract RequestBody buildRequestBody();

    AbsRequest(String url){
        this.url=url;
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

    public <T> void enqueue(HttpCallback<T> callback)
    {
        RxHttp.getInstance().enqueue(this,callback);
    }
}

