package com.donghanx.network.client

import io.ktor.client.HttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScryfallHttpClient @Inject constructor() {
    operator fun invoke(): HttpClient = client

    private val client: HttpClient by lazy { baseHttpClient(SCRYFALL_BASE_URL) }
}

private const val SCRYFALL_BASE_URL = "api.scryfall.com"
