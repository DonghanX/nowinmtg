package com.donghanx.network

import com.donghanx.model.NetworkSet
import com.donghanx.model.NetworkSets
import com.donghanx.network.client.ScryfallHttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import javax.inject.Inject

class SetsRemoteDataSource
@Inject
constructor(private val scryfallHttpClient: ScryfallHttpClient) {
    suspend fun getAllSets(): List<NetworkSet> =
        scryfallHttpClient().get { url(path = "/sets") }.body<NetworkSets>().data
}
