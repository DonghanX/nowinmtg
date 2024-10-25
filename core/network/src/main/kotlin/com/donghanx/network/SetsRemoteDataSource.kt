package com.donghanx.network

import com.donghanx.model.network.NetworkCardDetails
import com.donghanx.model.network.NetworkSet
import com.donghanx.model.network.NetworkSetDetails
import com.donghanx.model.network.NetworkSets
import com.donghanx.network.client.ScryfallHttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import javax.inject.Inject

class SetsRemoteDataSource @Inject constructor(private val scryfallHttpClient: ScryfallHttpClient) {
    suspend fun getAllSets(): List<NetworkSet> =
        scryfallHttpClient().get { url(path = "/sets") }.body<NetworkSets>().data

    suspend fun getCardsInCurrentSet(searchUri: String): List<NetworkCardDetails> =
        scryfallHttpClient()
            .get { url(urlString = searchUri.removePrefix(ScryfallHttpClient.BASE_URL)) }
            .body<NetworkSetDetails>()
            .cards
}
