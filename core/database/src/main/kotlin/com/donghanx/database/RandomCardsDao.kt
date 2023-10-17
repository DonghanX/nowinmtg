package com.donghanx.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.donghanx.database.model.RandomCardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RandomCardsDao {

    @Query("SELECT * FROM random_cards") fun getRandomCards(): Flow<List<RandomCardEntity>>

    @Insert suspend fun insertRandomCards(randomCards: List<RandomCardEntity>)

    @Query("DELETE FROM random_cards") suspend fun deleteAllRandomCards()

    @Transaction
    suspend fun deleteAllAndInsertRandomCards(randomCards: List<RandomCardEntity>) {
        deleteAllRandomCards()
        insertRandomCards(randomCards)
    }

    @Query("SELECT COUNT(*) FROM random_cards")
    suspend fun getRandomCardsCount(): Int
}
