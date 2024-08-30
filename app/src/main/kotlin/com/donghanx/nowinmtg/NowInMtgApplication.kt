package com.donghanx.nowinmtg

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import okhttp3.tls.HandshakeCertificates

@HiltAndroidApp
class NowInMtgApplication : Application(), ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(applicationContext)
            .okHttpClient {
                val clientCertificates =
                    HandshakeCertificates.Builder()
                        .addPlatformTrustedCertificates()
                        .addInsecureHost(INSECURE_MTG_HOST_NAME)
                        .build()

                OkHttpClient.Builder()
                    .sslSocketFactory(
                        clientCertificates.sslSocketFactory(),
                        clientCertificates.trustManager,
                    )
                    .build()
            }
            .components { add(SvgDecoder.Factory()) }
            .build()
    }
}

/** A insecure host for fetching MTG card images */
private const val INSECURE_MTG_HOST_NAME = "gatherer.wizards.com"
