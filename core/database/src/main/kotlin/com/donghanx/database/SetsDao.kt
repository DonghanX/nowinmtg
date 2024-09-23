package com.donghanx.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.donghanx.database.model.SetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SetsDao {

    @Query("SELECT * FROM sets") fun getAllSets(): Flow<List<SetEntity>>

    @Upsert suspend fun upsertSets(sets: List<SetEntity>)

    @Query(
        "SELECT * FROM sets WHERE name LIKE ('%' || :query || '%') OR code LIKE ('%' || :query || '%')"
    )
    fun searchAllSetsByQuery(query: String): Flow<List<SetEntity>>

    @Query("SELECT COUNT(*) FROM sets") fun getSetsCount(): Int

    @Query("SELECT * FROM sets WHERE scryfallId = :id") fun getSetById(id: String): Flow<SetEntity>
}
