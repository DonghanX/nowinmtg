package com.donghanx.data.repository.cards

import com.donghanx.common.NetworkResult
import com.donghanx.model.Card
import kotlinx.coroutines.flow.Flow

interface CardsRepository {

    fun getRandomCards(): Flow<List<Card>>

    fun refreshRandomCards(): Flow<NetworkResult<Unit>>
}