package com.donghanx.data.repository.setdetails

import com.donghanx.common.NetworkResult
import com.donghanx.model.CardPreview
import kotlinx.coroutines.flow.Flow

interface SetDetailsRepository {

    fun refreshCardsInCurrentSet(searchUri: String): Flow<NetworkResult<Unit>>

    fun getCardsInCurrentSet(setCode: String): Flow<List<CardPreview>>
}
