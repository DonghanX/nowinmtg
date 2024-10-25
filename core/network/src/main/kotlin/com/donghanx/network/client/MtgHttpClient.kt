package com.donghanx.network.client

import io.ktor.client.HttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MtgHttpClient @Inject constructor() {

    companion object {
        private const val MTG_BASE_URL = "api.magicthegathering.io/v1"
    }

    operator fun invoke(): HttpClient = client

    private val client: HttpClient by lazy { baseHttpClient(MTG_BASE_URL) }
}

