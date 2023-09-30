package com.donghanx.data.repository.cards

import com.donghanx.model.Card
import kotlinx.coroutines.flow.Flow

interface CardsRepository {

    fun defaultCards(): Flow<List<Card>>

    fun randomCards(): Flow<List<Card>>
}