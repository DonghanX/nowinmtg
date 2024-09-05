package com.donghanx.data.repository.carddetails

import com.donghanx.common.NetworkResult
import com.donghanx.model.CardDetails
import kotlinx.coroutines.flow.Flow

interface CardDetailsRepository {
    fun getCardDetailsById(cardId: String): Flow<CardDetails?>

    fun getCardDetailsByMultiverseId(multiverseId: Int): Flow<CardDetails?>

    fun refreshCardDetailsById(cardId: String): Flow<NetworkResult<Unit>>

    fun refreshCardDetailsByMultiverseId(multiverseId: Int): Flow<NetworkResult<Unit>>
}
