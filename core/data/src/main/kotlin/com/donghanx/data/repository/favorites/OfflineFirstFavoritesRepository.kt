package com.donghanx.data.repository.favorites

import com.donghanx.database.FavoritesDao
import com.donghanx.database.model.FavoriteCardEntity
import com.donghanx.database.model.asExternalModel
import com.donghanx.database.model.asFavoriteCardEntity
import com.donghanx.model.CardDetails
import com.donghanx.model.CardPreview
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class OfflineFirstFavoritesRepository
@Inject
constructor(
    private val favoritesDao: FavoritesDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : FavoritesRepository {
    override fun getFavoriteCards(): Flow<List<CardPreview>> =
        favoritesDao
            .getFavoriteCards()
            .map { it.map(FavoriteCardEntity::asExternalModel) }
            .flowOn(ioDispatcher)

    override suspend fun favoriteCardOrUndo(cardDetails: CardDetails) {
        withContext(ioDispatcher) {
            val favoriteCardEntity = cardDetails.asFavoriteCardEntity()
            with(favoritesDao) {
                if (isCardFavorite(favoriteCardEntity.id)) {
                    deleteFavoriteCard(favoriteCardEntity)
                } else {
                    upsertFavoriteCard(favoriteCardEntity)
                }
            }
        }
    }

    override fun observeIsCardFavorite(cardId: String): Flow<Boolean> =
        favoritesDao.observeIsCardFavorite(cardId)
}
