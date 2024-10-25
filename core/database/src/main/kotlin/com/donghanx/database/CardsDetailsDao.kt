package com.donghanx.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.donghanx.database.model.CardDetailsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDetailsDao {
    @Upsert suspend fun upsertCardDetails(cardDetails: CardDetailsEntity)

    @Upsert suspend fun upsertCardDetailsList(cardDetailsCollection: List<CardDetailsEntity>)

    @Query("SELECT * FROM card_details WHERE id = :cardId ")
    fun getCardDetailsById(cardId: String): Flow<CardDetailsEntity?>

    @Query("SELECT * FROM card_details WHERE `set` = :setCode ")
    fun getCardDetailsListBySetCode(setCode: String): Flow<List<CardDetailsEntity>>

    @Query("SELECT * FROM card_details WHERE multiverseId = :multiverseId ")
    fun getCardDetailsByMultiverseId(multiverseId: Int): Flow<CardDetailsEntity?>
}
