package com.donghanx.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.donghanx.database.converter.RulingsConverter
import com.donghanx.database.converter.StringListTypeConverters
import com.donghanx.database.model.CardDetailsEntity
import com.donghanx.database.model.FavoriteCardEntity
import com.donghanx.database.model.RandomCardEntity
import com.donghanx.database.model.SetEntity

@Database(
    entities =
        [
            RandomCardEntity::class,
            CardDetailsEntity::class,
            SetEntity::class,
            FavoriteCardEntity::class
        ],
    version = 1
)
@TypeConverters(StringListTypeConverters::class, RulingsConverter::class)
abstract class NimDatabase : RoomDatabase() {

    abstract fun randomCardsDao(): RandomCardsDao

    abstract fun cardDetailsDao(): CardDetailsDao

    abstract fun setsDao(): SetsDao

    abstract fun favoriteDao(): FavoritesDao

    abstract fun transactionRunnerDao(): TransactionRunnerDao
}
