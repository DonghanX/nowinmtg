package com.donghanx.network.client

import io.ktor.client.HttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScryfallHttpClient @Inject constructor() {

    companion object {
        const val BASE_URL = "api.scryfall.com"
    }

    operator fun invoke(): HttpClient = client

    private val client: HttpClient by lazy { baseHttpClient(BASE_URL) }
}
