package com.github.kongpf8848.rxhttp;

import com.github.kongpf8848.rxhttp.request.AbsRequest;

import java.io.IOException;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpInterceptor implements Interceptor {

    private ThreadLocal<AbsRequest>request=new ThreadLocal<AbsRequest>(){
        @Override
        protected AbsRequest initialValue() {
            return null;
        }
    };
    public void setRequest(AbsRequest request){
        this.request.set(request);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest=chain.request();
        Request.Builder builder=originalRequest.newBuilder();
        final AbsRequest okHttpRequest=this.request.get();
        if(okHttpRequest!=null){
            if(okHttpRequest.getTag()!=null){
                builder.tag(okHttpRequest.getTag());
            }
            Map<String,String> headers=okHttpRequest.getHeaders();
            if(headers!=null && !headers.isEmpty()){
                Headers.Builder headerBuilder=new Headers.Builder();
                for(String key:headers.keySet()){
                    headerBuilder.add(key,headers.get(key));
                }
                builder.headers(headerBuilder.build());
            }
        }
        Request newRequest=builder.build();
        return chain.proceed(newRequest);
    }

}
