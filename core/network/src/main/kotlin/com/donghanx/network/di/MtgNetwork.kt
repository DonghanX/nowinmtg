package com.donghanx.network.di

import com.donghanx.model.Card
import com.donghanx.model.Cards
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MtgNetwork @Inject constructor(private val mtgHttpClient: MtgHttpClient) {

    suspend fun getCards(): List<Card> =
        mtgHttpClient().get { url(path = "/cards") }.body<Cards>().cards

    suspend fun getRandomCards(pageSize: Int = 100): List<Card> =
        mtgHttpClient()
            .get {
                url(path = "/cards")
                parameter(key = "random", value = true)
                parameter(key = "pageSize", value = pageSize)
            }
            .body<Cards>()
            .cards
}
