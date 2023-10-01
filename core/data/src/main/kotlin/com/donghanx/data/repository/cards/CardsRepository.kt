package com.donghanx.data.repository.cards

import com.donghanx.model.Card
import kotlinx.coroutines.flow.Flow

interface CardsRepository {

    fun getRandomCards(): Flow<List<Card>>

    suspend fun refreshRandomCards()
}