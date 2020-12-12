package com.github.kongpf8848.rxhttp;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class RxHttpConfig {

    private static RxHttpConfig instance;

    private OkHttpClient.Builder builder;
    private int maxRetries;
    private long retryDelayMillis;

    public static RxHttpConfig getInstance() {
        if (instance == null) {
            synchronized (RxHttpConfig.class) {
                if (instance == null) {
                    instance = new RxHttpConfig();
                }
            }
        }
        return instance;
    }

    private RxHttpConfig() {

    }

    private OkHttpClient.Builder defaultBuilder(){
        OkHttpClient.Builder builder= new OkHttpClient.Builder();
        builder.connectTimeout(HttpConstants.TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(HttpConstants.TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(HttpConstants.TIME_OUT, TimeUnit.SECONDS);
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{UnSafeTrustManager}, null);
            builder.sslSocketFactory(sslContext.getSocketFactory(),UnSafeTrustManager);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return builder;
    }


    public RxHttpConfig builder(OkHttpClient.Builder builder) {
        this.builder = builder;
        return this;
    }


    public RxHttpConfig retryDelayMillis(long retryDelayMillis){
        this.retryDelayMillis=retryDelayMillis;
        return this;
    }

    public RxHttpConfig maxRetries(int maxRetries){
        this.maxRetries=maxRetries;
        return this;
    }

    public OkHttpClient.Builder getBuilder() {
        if (builder == null) {
            builder =defaultBuilder();
        }
        return builder;
    }


    public int getMaxRetries() {
        return maxRetries;
    }

    public long getRetryDelayMillis() {
        return retryDelayMillis;
    }

    public static X509TrustManager UnSafeTrustManager = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    };
}

