package com.donghanx.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.donghanx.database.model.RandomCardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RandomCardsDao {

    @Query("SELECT * FROM random_cards") fun getRandomCards(): Flow<List<RandomCardEntity>>

    @Upsert suspend fun upsertRandomCards(randomCards: List<RandomCardEntity>)
}
