package com.donghanx.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.donghanx.database.model.RulingsEntity

@Dao
interface RulingsDao {
    @Upsert fun upsertRuling(ruling: RulingsEntity)

    @Query("SELECT * FROM rulings WHERE cardId = :cardId ")
    suspend fun getRulingsByCardId(cardId: String): RulingsEntity?
}
