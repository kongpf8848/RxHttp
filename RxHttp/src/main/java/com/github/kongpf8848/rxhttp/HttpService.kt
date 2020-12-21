package com.github.kongpf8848.rxhttp

import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface HttpService {
    /**
     * Get请求
     * @param url
     * @return
     */
    @GET
    fun get(@Url url: String?, @QueryMap map: @JvmSuppressWildcards Map<String, Any?>?): Observable<ResponseBody?>?

    /**
     * Post请求
     * @param url
     * @param body
     * @return
     */
    @POST
    fun post(@Url url: String?, @Body body: RequestBody?): Observable<ResponseBody?>?

    /**
     * Post表单请求
     * @param url
     * @param map
     * @return
     */
    @POST
    @FormUrlEncoded
    fun postForm(@Url url: String?, @FieldMap(encoded = true) map: @JvmSuppressWildcards Map<String, Any?>?): Observable<ResponseBody?>?

    /**
     * Put请求
     * @param url
     * @param body
     * @return
     */
    @PUT
    fun put(@Url url: String?, @Body body: RequestBody?): Observable<ResponseBody?>?

    /**
     * Delete请求
     * @param url
     * @param map
     * @return
     */
    @HTTP(method = "DELETE", hasBody = true)
    fun delete(@Url url: String?, @Body map: @JvmSuppressWildcards Map<String, Any?>?): Observable<ResponseBody?>?

    /**
     * 下载请求
     * @param url
     * @return
     */
    @GET
    @Streaming
    fun download(@Url url: String?): Observable<ResponseBody?>?

    /**
     * 下载请求
     * @param url
     * @param range
     * @return
     */
    @GET
    @Streaming
    fun download(@Url url: String?, @Header("RANGE") range: String?): Observable<ResponseBody?>?

    /**
     * HEAD请求
     * @param url
     * @return
     */
    @HEAD
    fun head(@Url url: String?): Call<Void?>
}