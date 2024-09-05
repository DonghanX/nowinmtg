package com.donghanx.domain

import com.donghanx.data.repository.favorites.FavoritesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveIsCardFavoriteUseCase
@Inject
constructor(private val favoritesRepository: FavoritesRepository) {

    operator fun invoke(cardIdFlow: Flow<String?>, multiverseIdFlow: Flow<Int>): Flow<Boolean> =
        flatMapValidCardIdFlow(
            cardIdFlow = cardIdFlow,
            multiverseIdFlow = multiverseIdFlow,
            flatMapById = favoritesRepository::observeIsCardFavoriteByCardId,
            flatMapByMultiverseId = favoritesRepository::observeIsCardFavoriteByMultiverseId,
        )
}
