package io.github.kongpf8848.rxhttp.util

import java.io.IOException
import java.io.InputStream
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*

object SSLUtil {

    private val UnSafeTrustManager: X509TrustManager = object : X509TrustManager {
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

    fun getSSLSocketFactory(certificates: List<InputStream>?): Pair<SSLSocketFactory, X509TrustManager> {
        try {
            var manager: X509TrustManager? = null
            val trustManagers = prepareTrustManager(certificates)
            if (trustManagers != null) {
                manager = chooseTrustManager(trustManagers)
            }
            if (manager == null) {
                manager = UnSafeTrustManager
            }
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, arrayOf<TrustManager>(manager),SecureRandom())
            return Pair(sslContext.socketFactory, manager)
        } catch (e: NoSuchAlgorithmException) {
            throw e
        } catch (e: KeyManagementException) {
            throw e
        }
    }

    private fun prepareTrustManager(certificates: List<InputStream>?): Array<TrustManager>? {
        if (certificates.isNullOrEmpty()) {
            return null
        }
        try {
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null, null)
            var index = 0
            for (certStream in certificates) {
                val certificateAlias = (index++).toString()
                val certificate = certificateFactory.generateCertificate(certStream)
                keyStore.setCertificateEntry(certificateAlias, certificate)
                try {
                    certStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            tmf.init(keyStore)
            return tmf.trustManagers
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun chooseTrustManager(trustManagers: Array<TrustManager>): X509TrustManager? {
        for (trustManager in trustManagers) {
            if (trustManager is X509TrustManager) {
                return trustManager
            }
        }
        return null
    }
}