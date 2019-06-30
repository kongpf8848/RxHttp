package com.github.kongpf8848.rxhttp;

import com.github.kongpf8848.rxhttp.bean.DownloadInfo;
import com.github.kongpf8848.rxhttp.bean.UploadInfo;
import com.github.kongpf8848.rxhttp.callback.DownloadCallback;
import com.github.kongpf8848.rxhttp.callback.HttpCallback;
import com.github.kongpf8848.rxhttp.callback.ProgressCallback;
import com.github.kongpf8848.rxhttp.callback.UploadCallback;
import com.github.kongpf8848.rxhttp.converter.DownloadConverter;
import com.github.kongpf8848.rxhttp.converter.GsonConverter;
import com.github.kongpf8848.rxhttp.request.DownloadRequest;
import com.github.kongpf8848.rxhttp.request.GetRequest;
import com.github.kongpf8848.rxhttp.request.AbsRequest;
import com.github.kongpf8848.rxhttp.request.PostFormRequest;
import com.github.kongpf8848.rxhttp.request.PostRequest;
import com.github.kongpf8848.rxhttp.request.UploadRequest;
import com.github.kongpf8848.rxhttp.util.LogUtil;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RxHttp {

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private HttpInterceptor interceptor;
    private HttpService httpService;
    private static RxHttp instance;

    public static RxHttp getInstance() {
        if (instance == null) {
            synchronized (RxHttp.class) {
                if (instance == null) {
                    instance = new RxHttp();
                }
            }
        }
        return instance;
    }


    private RxHttp() {
        HttpConfig config=HttpConfig.getInstance();

        LogUtil.setDebug(config.isDebug());

        OkHttpClient.Builder builder = config.getBuilder();
        interceptor = new HttpInterceptor();
        builder.addInterceptor(interceptor);
        okHttpClient = builder.build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://example.com")
                .build();
        httpService = retrofit.create(HttpService.class);
    }


    //get请求
    public GetRequest get(final String url) {
        return new GetRequest(url);
    }

    //post请求
    public PostRequest post(final String url) {
        return new PostRequest(url);
    }

    //post表单请求
    public PostFormRequest postForm(final String url) {
        return new PostFormRequest(url);
    }

    //upload请求
    public UploadRequest upload(final String url) {
        return new UploadRequest(url);
    }

    //download请求
    public DownloadRequest download(final String url) {
        return new DownloadRequest(url);
    }


    public <T> void enqueue(final AbsRequest request, final HttpCallback<T> callback) {
        interceptor.setRequest(request);
        Observable<ResponseBody> observable = null;
        if (request instanceof GetRequest) {
            observable = httpService.get(request.getUrl());
        } else if (request instanceof PostRequest) {
            PostRequest postRequest = (PostRequest) request;
            observable = httpService.post(postRequest.getUrl(), postRequest.buildRequestBody());
        } else if (request instanceof PostFormRequest) {
            observable = httpService.postForm(request.getUrl(), request.getParams());
        } else if (request instanceof UploadRequest) {
            final UploadRequest uploadRequest = (UploadRequest) request;
            final UploadInfo uploadInfo = new UploadInfo(uploadRequest.getUrl());
            final ProgressRequestBody requestBody = new ProgressRequestBody(uploadRequest.buildRequestBody(), new ProgressCallback() {
                @Override
                public void onProgress(long totalBytes, long readBytes) {
                    uploadInfo.setProgress(readBytes);
                    Platform.get().defaultCallbackExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            ((UploadCallback) callback).onProgress(uploadInfo);
                        }
                    });
                }
            });

            try {
                uploadInfo.setTotal(requestBody.contentLength());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Platform.get().defaultCallbackExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    ((UploadCallback) callback).onProgress(uploadInfo);
                }
            });

            observable = httpService.post(uploadRequest.getUrl(), requestBody);
        } else if (request instanceof DownloadRequest) {
            observable = httpService.download(request.getUrl());
        }
        if (observable != null) {
            observable.map(new Function<ResponseBody, T>() {
                @Override
                public T apply(ResponseBody body) throws Exception {
                    if (request instanceof DownloadRequest) {
                        DownloadRequest downloadRequest = (DownloadRequest) request;
                        DownloadInfo downloadInfo = new DownloadInfo(downloadRequest.getUrl(), downloadRequest.getDir());
                        DownloadConverter<T> downloadConverter = new DownloadConverter(downloadInfo, (DownloadCallback) callback);
                        return downloadConverter.convert(body, callback.getType());
                    } else {
                        return new GsonConverter<T>().convert(body, callback.getType());

                    }
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new HttpObserver<T>(callback));
        }

    }


}
