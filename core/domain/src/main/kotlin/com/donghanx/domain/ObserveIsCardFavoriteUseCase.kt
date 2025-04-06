package com.donghanx.domain

import com.donghanx.data.repository.favorites.FavoritesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveIsCardFavoriteUseCase
@Inject
constructor(private val favoritesRepository: FavoritesRepository) {

    operator fun invoke(cardId: String?, multiverseId: Int?): Flow<Boolean> =
        mapValidCardIdFlow(
            cardId = cardId,
            multiverseId = multiverseId,
            flowById = favoritesRepository::observeIsCardFavoriteByCardId,
            flowByMultiverseId = favoritesRepository::observeIsCardFavoriteByMultiverseId,
        )
}
