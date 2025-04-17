package com.donghanx.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.donghanx.database.model.SetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SetsDao {

    @Query("SELECT * FROM sets") fun getAllSets(): Flow<List<SetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertSets(sets: List<SetEntity>)

    @Query("DELETE FROM sets") suspend fun deleteAllSets()

    @Transaction
    suspend fun deleteAllAndInsertSets(sets: List<SetEntity>) {
        deleteAllSets()
        insertSets(sets)
    }

    @Query(
        "SELECT * FROM sets WHERE name LIKE ('%' || :query || '%') OR code LIKE ('%' || :query || '%')"
    )
    fun searchAllSetsByQuery(query: String): Flow<List<SetEntity>>

    @Query("SELECT COUNT(*) FROM sets") fun getSetsCount(): Int

    @Query("SELECT * FROM sets WHERE scryfallId = :id") fun getSetById(id: String): Flow<SetEntity>
}
