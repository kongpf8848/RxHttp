package com.github.kongpf8848.rxhttp;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface HttpService {

    /**
     * Get请求
     * @param url
     * @return
     */
    @GET
    Observable<ResponseBody> get(@Url String url,@QueryMap Map<String, Object> map);

    /**
     * Post请求
     * @param url
     * @param body
     * @return
     */
    @POST
    Observable<ResponseBody>post(@Url String url, @Body RequestBody body);

    /**
     * Post表单请求
     * @param url
     * @param map
     * @return
     */
    @POST
    @FormUrlEncoded
    Observable<ResponseBody> postForm(@Url String url, @FieldMap Map<String, Object> map);

    /**
     * Put请求
     * @param url
     * @param body
     * @return
     */
    @PUT
    Observable<ResponseBody> put(@Url String url, @Body RequestBody body);

    /**
     * Delete请求
     * @param url
     * @param map
     * @return
     */
    @HTTP(method = "DELETE",hasBody = true)
    Observable<ResponseBody> delete(@Url String url,@Body Map<String, Object> map);

    /**
     * 下载请求
     * @param url
     * @return
     */
    @GET
    @Streaming
    Observable<ResponseBody> download(@Url String url);

    /**
     * 下载请求
     * @param url
     * @param range
     * @return
     */
    @GET
    @Streaming
    Observable<ResponseBody> download(@Url String url, @Header("RANGE") String range);


}
