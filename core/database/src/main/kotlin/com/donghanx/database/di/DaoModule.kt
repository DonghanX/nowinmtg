package com.donghanx.database.di

import com.donghanx.database.CardDetailsDao
import com.donghanx.database.FavoritesDao
import com.donghanx.database.NimDatabase
import com.donghanx.database.RandomCardsDao
import com.donghanx.database.SetsDao
import com.donghanx.database.TransactionRunnerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun provideRandomCardsDao(nimDatabase: NimDatabase): RandomCardsDao =
        nimDatabase.randomCardsDao()

    @Provides
    fun provideCardDetailsDao(nimDatabase: NimDatabase): CardDetailsDao =
        nimDatabase.cardDetailsDao()

    @Provides fun providesSetsDao(nimDatabase: NimDatabase): SetsDao = nimDatabase.setsDao()

    @Provides
    fun provideFavoritesDao(nimDatabase: NimDatabase): FavoritesDao = nimDatabase.favoriteDao()

    @Provides
    fun provideTransactionRunner(nimDatabase: NimDatabase): TransactionRunnerDao =
        nimDatabase.transactionRunnerDao()
}
