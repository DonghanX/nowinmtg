package com.donghanx.data.repository.carddetails

import com.donghanx.common.NetworkResult
import com.donghanx.model.CardDetails
import com.donghanx.model.Ruling
import kotlinx.coroutines.flow.Flow

interface CardDetailsRepository {
    fun getCardDetailsById(cardId: String): Flow<CardDetails?>

    fun getCardDetailsByMultiverseId(multiverseId: Int): Flow<CardDetails?>

    fun refreshCardDetailsById(cardId: String): Flow<NetworkResult<Unit>>

    fun refreshCardDetailsByMultiverseId(multiverseId: Int): Flow<NetworkResult<Unit>>

    suspend fun oneshotGetCardRulingsById(cardId: String): List<Ruling>

    fun refreshCardRulingsById(cardId: String): Flow<NetworkResult<Unit>>
}
