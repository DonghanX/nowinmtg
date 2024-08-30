package com.donghanx.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.donghanx.database.model.CardDetailsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDetailsDao {
    @Upsert suspend fun upsertCardDetails(cardDetails: CardDetailsEntity)

    @Query("SELECT * FROM card_details WHERE multiverseId = :cardId ")
    fun getCardDetailsById(cardId: Int): Flow<CardDetailsEntity?>
}
