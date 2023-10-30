package com.donghanx.network.di

import com.donghanx.model.NetworkCard
import com.donghanx.model.NetworkCardDetails
import com.donghanx.model.NetworkCardDetailsList
import com.donghanx.model.NetworkCards
import com.donghanx.model.NetworkSet
import com.donghanx.model.NetworkSets
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

    suspend fun getCardDetailsById(cardId: String): NetworkCardDetails =
        mtgHttpClient()
            .get {
                url(path = "/cards")
                parameter(key = "id", value = cardId)
            }
            .body<NetworkCardDetailsList>()
            .cards
            .first()

    suspend fun getAllSets(): List<NetworkSet> =
        mtgHttpClient().get { url(path = "/sets") }.body<NetworkSets>().sets
}
