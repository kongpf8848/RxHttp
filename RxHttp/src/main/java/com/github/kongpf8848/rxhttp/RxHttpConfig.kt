package com.github.kongpf8848.rxhttp

import okhttp3.OkHttpClient
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class RxHttpConfig  {


     var okhttpBuilder: OkHttpClient.Builder? = null
    private set

     var maxRetries = 0
     var retryDelayMillis: Long = 0

    private fun defaultBuilder(): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(HttpConstants.TIME_OUT.toLong(), TimeUnit.SECONDS)
        builder.readTimeout(HttpConstants.TIME_OUT.toLong(), TimeUnit.SECONDS)
        builder.writeTimeout(HttpConstants.TIME_OUT.toLong(), TimeUnit.SECONDS)
        try {
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, arrayOf<TrustManager>(UnSafeTrustManager), null)
            builder.sslSocketFactory(sslContext.socketFactory, UnSafeTrustManager)
            builder.hostnameVerifier { _, _ -> true }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }
        return builder
    }

    fun builder(builder: OkHttpClient.Builder?): RxHttpConfig {
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

    fun getBuilder(): OkHttpClient.Builder {
        if (okhttpBuilder == null) {
            okhttpBuilder = defaultBuilder()
        }
        return okhttpBuilder!!
    }

    companion object {

        private object RxHttpConfig2 {
            val holder = RxHttpConfig()
        }

        fun getInstance() = RxHttpConfig2.holder

        val UnSafeTrustManager: X509TrustManager = object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return emptyArray()
            }
        }
    }
}