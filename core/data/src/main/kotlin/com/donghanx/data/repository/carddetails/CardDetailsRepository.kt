package com.donghanx.data.repository.carddetails

import com.donghanx.common.NetworkResult
import com.donghanx.model.CardDetails
import kotlinx.coroutines.flow.Flow

interface CardDetailsRepository {
    fun getCardDetailsById(cardId: Int): Flow<CardDetails?>

    fun refreshCardDetails(cardId: Int): Flow<NetworkResult<Unit>>
}
