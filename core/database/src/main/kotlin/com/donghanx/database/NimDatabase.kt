package com.donghanx.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.donghanx.database.converter.StringListTypeConverters
import com.donghanx.database.model.RandomCardEntity

@Database(entities = [RandomCardEntity::class], version = 1)
@TypeConverters(StringListTypeConverters::class)
abstract class NimDatabase : RoomDatabase() {

    abstract fun randomCardsDao(): RandomCardsDao

    abstract fun cardsDetailsDao(): CardsDetailsDao

    abstract fun transactionRunnerDao(): TransactionRunnerDao
}
