package com.donghanx.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.donghanx.database.model.FavoriteCardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favorite_card") fun getFavoriteCards(): Flow<List<FavoriteCardEntity>>

    @Upsert suspend fun upsertFavoriteCard(favoriteCardEntity: FavoriteCardEntity)

    @Delete suspend fun deleteFavoriteCard(favoriteCardEntity: FavoriteCardEntity)

    @Query("SELECT EXISTS(SELECT * FROM favorite_card WHERE multiverseId = :cardId)")
    fun observeIsCardFavorite(cardId: Int): Flow<Boolean>

    @Query("SELECT EXISTS(SELECT * FROM favorite_card WHERE multiverseId = :cardId)")
    suspend fun isCardFavorite(cardId: Int): Boolean
}
