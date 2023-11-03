package com.donghanx.data.di

import com.donghanx.data.repository.carddetails.CardDetailsRepository
import com.donghanx.data.repository.carddetails.OfflineFirstCardDetailsRepository
import com.donghanx.data.repository.cards.OfflineFirstRandomCardsRepository
import com.donghanx.data.repository.cards.RandomCardsRepository
import com.donghanx.data.repository.sets.OfflineFirstSetsRepository
import com.donghanx.data.repository.sets.SetsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindCardsRepository(
        offlineFirstRandomCardsRepository: OfflineFirstRandomCardsRepository
    ): RandomCardsRepository

    @Binds
    fun bindCardDetailsRepository(
        offlineFirstCardDetailsRepository: OfflineFirstCardDetailsRepository
    ): CardDetailsRepository

    @Binds
    fun bindSetsRepository(offlineFirstSetsRepository: OfflineFirstSetsRepository): SetsRepository
}
