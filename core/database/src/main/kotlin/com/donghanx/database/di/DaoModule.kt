package com.donghanx.database.di

import com.donghanx.database.CardsDetailsDao
import com.donghanx.database.NimDatabase
import com.donghanx.database.RandomCardsDao
import com.donghanx.database.TransactionRunner
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
    fun provideCardsDetailsDao(nimDatabase: NimDatabase): CardsDetailsDao =
        nimDatabase.cardsDetailsDao()

    @Provides
    fun provideTransactionRunner(nimDatabase: NimDatabase): TransactionRunnerDao =
        nimDatabase.transactionRunnerDao()
}
