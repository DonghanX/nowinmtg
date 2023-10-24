package com.donghanx.data.repository.cards

import com.donghanx.common.NetworkResult
import com.donghanx.model.CardPreview
import kotlinx.coroutines.flow.Flow

interface CardsRepository {

    fun getRandomCards(): Flow<List<CardPreview>>

    fun refreshRandomCards(shouldContainImageUrl: Boolean): Flow<NetworkResult<Unit>>

    suspend fun shouldFetchInitialCards(): Boolean
}