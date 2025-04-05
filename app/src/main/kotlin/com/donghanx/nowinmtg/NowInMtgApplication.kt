package com.donghanx.nowinmtg

import android.app.Application
import android.content.Context
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.svg.SvgDecoder
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import okhttp3.tls.HandshakeCertificates

@HiltAndroidApp
class NowInMtgApplication : Application(), SingletonImageLoader.Factory {

    override fun newImageLoader(context: Context): ImageLoader {
        return ImageLoader.Builder(applicationContext)
            .components {
                add(
                    OkHttpNetworkFetcherFactory(
                        callFactory = {
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
                    )
                )
                add(SvgDecoder.Factory())
            }
            .build()
    }
}

/** A insecure host for fetching MTG card images */
private const val INSECURE_MTG_HOST_NAME = "gatherer.wizards.com"
