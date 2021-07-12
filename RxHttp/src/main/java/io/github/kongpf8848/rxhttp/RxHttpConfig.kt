package io.github.kongpf8848.rxhttp

import io.github.kongpf8848.rxhttp.util.SSLUtil
import okhttp3.OkHttpClient
import java.io.InputStream
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

class RxHttpConfig {

    private object Config {
        val holder = RxHttpConfig()
    }

    var okhttpBuilder: OkHttpClient.Builder? = null
    var maxRetries = 0
    var retryDelayMillis = 0L
    var certificates:MutableList<InputStream>?=null

    private fun defaultBuilder(): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder().apply {
            connectTimeout(HttpConstants.TIME_OUT, TimeUnit.SECONDS)
            readTimeout(HttpConstants.TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(HttpConstants.TIME_OUT, TimeUnit.SECONDS)
        }
        try {
            val sslParams= SSLUtil.getSSLSocketFactory(certificates =certificates)
            builder.sslSocketFactory(sslParams.first,sslParams.second)
            builder.hostnameVerifier(HostnameVerifier { _, _ -> true })
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }
        return builder
    }

    fun builder(builder: OkHttpClient.Builder): RxHttpConfig {
        this.okhttpBuilder = builder
        return this
    }

    fun retryDelayMillis(retryDelayMillis: Long): RxHttpConfig {
        this.retryDelayMillis = retryDelayMillis
        return this
    }

    fun maxRetries(maxRetries: Int): RxHttpConfig {
        this.maxRetries = maxRetries
        return this
    }

    fun certificate(cer:InputStream?):RxHttpConfig{
        if(this.certificates==null){
            this.certificates=ArrayList()
        }
        cer?.let {
            this.certificates!!.add(it)
        }
        return this
    }

    fun certificates(cerList:List<InputStream>?):RxHttpConfig{
        if(this.certificates==null){
            this.certificates=ArrayList()
        }
        cerList?.forEach {
            this.certificates!!.add(it)
        }
        return this
    }

    fun getBuilder(): OkHttpClient.Builder {
        if (okhttpBuilder == null) {
            okhttpBuilder = defaultBuilder()
        }
        return okhttpBuilder!!
    }

    companion object {
        fun getInstance() = Config.holder
    }
}