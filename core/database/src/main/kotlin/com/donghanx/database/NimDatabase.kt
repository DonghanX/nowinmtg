package com.donghanx.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.donghanx.database.model.RandomCardEntity

@Database(entities = [RandomCardEntity::class], version = 1)
abstract class NimDatabase : RoomDatabase() {

    abstract fun randomCardsDao(): RandomCardsDao
}
