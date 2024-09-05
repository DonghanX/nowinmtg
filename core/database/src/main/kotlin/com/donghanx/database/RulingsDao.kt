package com.donghanx.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.donghanx.database.model.RulingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RulingsDao {
    @Upsert fun upsertRulings(rulings: List<RulingsEntity>)

    @Query("SELECT * FROM rulings WHERE cardId = :cardId ")
    fun getRulingsByCardId(cardId: String): Flow<List<RulingsEntity>>
}
