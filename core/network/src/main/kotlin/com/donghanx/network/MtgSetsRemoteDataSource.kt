package com.donghanx.network

import com.donghanx.model.NetworkSet
import com.donghanx.model.NetworkSets
import com.donghanx.network.client.MtgHttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import javax.inject.Inject

class MtgSetsRemoteDataSource @Inject constructor(private val mtgHttpClient: MtgHttpClient) {
    suspend fun getAllSets(): List<NetworkSet> =
        mtgHttpClient().get { url(path = "/sets") }.body<NetworkSets>().sets
}
