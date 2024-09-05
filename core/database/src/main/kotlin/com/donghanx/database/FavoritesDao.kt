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

    @Query("SELECT EXISTS(SELECT * FROM favorite_card WHERE id = :cardId)")
    fun observeIsCardFavoriteByCardId(cardId: String): Flow<Boolean>

    @Query("SELECT EXISTS(SELECT * FROM favorite_card WHERE multiverseId = :multiverseId)")
    fun observeIsCardFavoriteByMultiverseId(multiverseId: Int): Flow<Boolean>

    @Query("SELECT EXISTS(SELECT * FROM favorite_card WHERE id = :cardId)")
    suspend fun isCardFavorite(cardId: String): Boolean
}
