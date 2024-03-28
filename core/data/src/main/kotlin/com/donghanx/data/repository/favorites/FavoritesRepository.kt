package com.donghanx.data.repository.favorites

import com.donghanx.model.CardDetails
import com.donghanx.model.CardPreview
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    fun getFavoriteCards(): Flow<List<CardPreview>>

    suspend fun favoriteCardOrUndo(cardDetails: CardDetails)

    fun observeIsCardFavorite(cardId: String): Flow<Boolean>
}
