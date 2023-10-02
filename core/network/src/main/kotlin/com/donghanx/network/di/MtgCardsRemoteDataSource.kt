package com.donghanx.network.di

import com.donghanx.model.NetworkCard
import com.donghanx.model.NetworkCards
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MtgCardsRemoteDataSource @Inject constructor(private val mtgHttpClient: MtgHttpClient) {

    suspend fun getRandomCards(pageSize: Int = 100): List<NetworkCard> =
        mtgHttpClient()
            .get {
                url(path = "/cards")
                parameter(key = "random", value = true)
                parameter(key = "pageSize", value = pageSize)
            }
            .body<NetworkCards>()
            .cards
}
