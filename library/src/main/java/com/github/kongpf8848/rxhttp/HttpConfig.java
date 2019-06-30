package com.github.kongpf8848.rxhttp;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class HttpConfig {

    private static HttpConfig instance;

    private OkHttpClient.Builder builder;
    private boolean debug;

    public static HttpConfig getInstance() {
        if (instance == null) {
            synchronized (HttpConfig.class) {
                if (instance == null) {
                    instance = new HttpConfig();
                }
            }
        }
        return instance;
    }

    private HttpConfig() {

    }

    public static OkHttpClient.Builder defaultBuilder(){
        OkHttpClient.Builder builder= new OkHttpClient.Builder();
        builder.connectTimeout(HttpConstants.TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(HttpConstants.TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(HttpConstants.TIME_OUT, TimeUnit.SECONDS);
        return builder;
    }


    public HttpConfig builder(OkHttpClient.Builder builder) {
        this.builder = builder;
        return this;
    }

    public HttpConfig debug(boolean debug) {
        this.debug = debug;
        return this;
    }


    public OkHttpClient.Builder getBuilder() {
        if (builder == null) {
            builder =defaultBuilder();
        }
        return builder;
    }


    public boolean isDebug() {
        return debug;
    }

}
