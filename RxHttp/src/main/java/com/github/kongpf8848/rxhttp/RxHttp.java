package com.github.kongpf8848.rxhttp;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.github.kongpf8848.rxhttp.bean.DownloadInfo;
import com.github.kongpf8848.rxhttp.callback.DownloadCallback;
import com.github.kongpf8848.rxhttp.callback.HttpCallback;
import com.github.kongpf8848.rxhttp.converter.DownloadConverter;
import com.github.kongpf8848.rxhttp.converter.GsonConverter;
import com.github.kongpf8848.rxhttp.request.AbsRequest;
import com.github.kongpf8848.rxhttp.request.DeleteRequest;
import com.github.kongpf8848.rxhttp.request.DownloadRequest;
import com.github.kongpf8848.rxhttp.request.GetRequest;
import com.github.kongpf8848.rxhttp.request.PostFormRequest;
import com.github.kongpf8848.rxhttp.request.PostRequest;
import com.github.kongpf8848.rxhttp.request.PutRequest;
import com.github.kongpf8848.rxhttp.request.UploadRequest;
import com.github.kongpf8848.rxhttp.util.Md5Util;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
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
        RxHttpConfig config = RxHttpConfig.getInstance();


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
    public GetRequest get(Context context) {
        return new GetRequest(context);
    }

    public GetRequest get(Activity activity) {
        return new GetRequest(activity);
    }

    public GetRequest get(Fragment fragment) {
        return new GetRequest(fragment);
    }

    public GetRequest get(LifecycleTransformer transformer) {
        return new GetRequest(transformer);
    }

    //post请求
    public PostRequest post(Context context) {
        return new PostRequest(context);
    }

    public PostRequest post(Activity activity) {
        return new PostRequest(activity);
    }

    public PostRequest post(Fragment fragment) {
        return new PostRequest(fragment);
    }

    public PostRequest post(LifecycleTransformer transformer) {
        return new PostRequest(transformer);
    }

    //post表单请求
    public PostFormRequest postForm(Context context) {
        return new PostFormRequest(context);
    }

    public PostFormRequest postForm(Activity activity) {
        return new PostFormRequest(activity);
    }

    public PostFormRequest postForm(Fragment fragment) {
        return new PostFormRequest(fragment);
    }

    public PostFormRequest postForm(LifecycleTransformer transformer) {
        return new PostFormRequest(transformer);
    }

    //upload请求
    public UploadRequest upload(Context context) {
        return new UploadRequest(context);
    }

    public UploadRequest upload(Activity activity) {
        return new UploadRequest(activity);
    }

    public UploadRequest upload(Fragment fragment) {
        return new UploadRequest(fragment);
    }

    public UploadRequest upload(LifecycleTransformer transformer) {
        return new UploadRequest(transformer);
    }

    //download请求
    public DownloadRequest download(Context context) {
        return new DownloadRequest(context);
    }

    public DownloadRequest download(Activity activity) {
        return new DownloadRequest(activity);
    }

    public DownloadRequest download(Fragment fragment) {
        return new DownloadRequest(fragment);
    }

    public DownloadRequest download(LifecycleTransformer transformer) {
        return new DownloadRequest(transformer);
    }

    //put请求
    public PutRequest put(Context context) {
        return new PutRequest(context);
    }
    public PutRequest put(Activity activity) {
        return new PutRequest(activity);
    }
    public PutRequest put(Fragment fragment) {
        return new PutRequest(fragment);
    }
    public PutRequest put(LifecycleTransformer transformer) {
        return new PutRequest(transformer);
    }

    //delete请求
    public DeleteRequest delete(Context context) {
        return new DeleteRequest(context);
    }
    public DeleteRequest delete(Activity activity) {
        return new DeleteRequest(activity);
    }
    public DeleteRequest delete(Fragment fragment) {
        return new DeleteRequest(fragment);
    }
    public DeleteRequest delete(LifecycleTransformer transformer) {
        return new DeleteRequest(transformer);
    }

    public <T> void enqueue(final AbsRequest request, final HttpCallback<T> callback) {
        interceptor.setRequest(request);
        Observable<ResponseBody> observable = null;
        if (request instanceof GetRequest) {
            observable = httpService.get(request.getUrl(),request.getParams());
        }
        else if(request instanceof PutRequest){
            PutRequest putRequest = (PutRequest) request;
            observable = httpService.put(putRequest.getUrl(), putRequest.buildRequestBody());
        }
        else if (request instanceof PostRequest) {
            PostRequest postRequest = (PostRequest) request;
            observable = httpService.post(postRequest.getUrl(), postRequest.buildRequestBody());
        }
        else if (request instanceof PostFormRequest) {
            observable = httpService.postForm(request.getUrl(), request.getParams());
        }
        else if(request instanceof DeleteRequest){
            observable = httpService.delete(request.getUrl(), request.getParams());
        }
        else if (request instanceof UploadRequest) {
            final UploadRequest uploadRequest = (UploadRequest) request;
            observable = httpService.post(uploadRequest.getUrl(), uploadRequest.buildRequestBody());
        } else if (request instanceof DownloadRequest) {
            final DownloadRequest downloadRequest = ((DownloadRequest) request);
            final File file=new File(downloadRequest.getDir(),downloadRequest.getFilename());
            DownloadInfo downloadInfo=new DownloadInfo(downloadRequest.getUrl(),downloadRequest.getDir(),downloadRequest.getFilename());
            if(!TextUtils.isEmpty(downloadRequest.getMd5())){
                if(file.exists()){
                    String fileMd5= Md5Util.getMD5(file);
                    if(downloadRequest.getMd5().equalsIgnoreCase(fileMd5)){
                        downloadInfo.setTotal(file.length());
                        downloadInfo.setProgress(file.length());
                        callback.onNext((T)downloadInfo);
                        return;
                    }
                }
            }
            if (downloadRequest.isBreakpoint()) {
                observable=Observable.just(downloadRequest.getUrl())
                        .flatMap(new Function<String, ObservableSource<Long>>() {
                    @Override
                    public ObservableSource<Long> apply(String url) throws Exception {
                        long contentLength = getContentLength(url);
                        long start=0;
                        if(file.exists()){
                            if(contentLength==-1 || file.length()>=contentLength){
                                file.delete();
                            }
                            else{
                                start=file.length();
                            }
                        }
                        return Observable.just(start);
                    }
                }).flatMap(new Function<Long, ObservableSource<ResponseBody>>() {
                    @Override
                    public ObservableSource<ResponseBody> apply(Long start) throws Exception {
                        return httpService.download(downloadRequest.getUrl(),String.format("bytes=%d-",start));
                    }
                }).subscribeOn(Schedulers.io());

            } else {
                observable = httpService.download(request.getUrl());
            }
        }


        if (observable != null) {
            Observable<T> observableFinal = observable.map(new Function<ResponseBody, T>() {
                @Override
                public T apply(ResponseBody body) throws Exception {
                    if (request instanceof DownloadRequest) {
                        DownloadConverter<T> downloadConverter = new DownloadConverter((DownloadRequest) request, (DownloadCallback) callback);
                        return downloadConverter.convert(body, callback.getType());
                    } else {
                        return new GsonConverter<T>().convert(body, callback.getType());

                    }
                }
            }).retryWhen(new RetryWithDelay(RxHttpConfig.getInstance().getMaxRetries(), RxHttpConfig.getInstance().getRetryDelayMillis()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            if (request.getContext() instanceof LifecycleOwner) {
                LifecycleOwner lifecycleOwner = (LifecycleOwner) request.getContext();
                observableFinal.as(AutoDispose.<T>autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, Lifecycle.Event.ON_DESTROY)))
                        .subscribeWith(new HttpObserver<T>(callback));
            } else if (request.getContext() instanceof LifecycleTransformer) {
                observableFinal.compose((LifecycleTransformer) request.getContext())
                        .subscribeWith(new HttpObserver<T>(callback));
            } else {
                observableFinal.subscribeWith(new HttpObserver<T>(callback));
            }


        }

    }


    private long getContentLength(String url) throws Exception {
        retrofit2.Response<Void> response=httpService.head(url).execute();
        if(response.isSuccessful()){
            String contentLength=response.headers().get("Content-Length");
            if(!TextUtils.isEmpty(contentLength)){
                return Long.parseLong(contentLength);
            }
        }
        return -1;
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        Response response = new OkHttpClient.Builder().build().newCall(request).execute();
//        if (response.isSuccessful()) {
//            long contentLength = response.body().contentLength();
//            response.close();
//            return contentLength;
//        }
//        return -1;
    }


}
