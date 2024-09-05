package com.donghanx.network

import com.donghanx.model.network.NetworkCard
import com.donghanx.model.network.NetworkCardDetails
import com.donghanx.model.network.NetworkCards
import com.donghanx.network.client.MtgHttpClient
import com.donghanx.network.client.ScryfallHttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MtgCardsRemoteDataSource
@Inject
constructor(
    private val mtgHttpClient: MtgHttpClient,
    private val scryfallHttpClient: ScryfallHttpClient,
) {

    suspend fun getRandomCards(pageSize: Int = 100): List<NetworkCard> =
        mtgHttpClient()
            .get {
                url(path = "/cards")
                parameter(key = "random", value = true)
                parameter(key = "contains", value = "multiverseId")
                parameter(key = "contains", value = "imageUrl")
                parameter(key = "pageSize", value = pageSize)
            }
            .body<NetworkCards>()
            .cards

    suspend fun getCardDetailsByCardId(cardId: String): NetworkCardDetails =
        scryfallHttpClient().get { url(path = "/cards/$cardId") }.body()

    suspend fun getCardDetailsByMultiverseId(multiverseId: Int): NetworkCardDetails =
        scryfallHttpClient().get { url(path = "/cards/multiverse/$multiverseId") }.body()
}
