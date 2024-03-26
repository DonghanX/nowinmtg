package com.donghanx.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.donghanx.database.model.FavoriteCardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favorite_card") fun getFavoriteCards(): Flow<List<FavoriteCardEntity>>

    @Upsert suspend fun upsertFavoriteCard(favoriteCardEntity: FavoriteCardEntity)
}
