package com.donghanx.data.di

import com.donghanx.data.repository.carddetails.CardDetailsRepository
import com.donghanx.data.repository.carddetails.OfflineFirstCardDetailsRepository
import com.donghanx.data.repository.cards.OfflineFirstRandomCardsRepository
import com.donghanx.data.repository.cards.RandomCardsRepository
import com.donghanx.data.repository.favorites.FavoritesRepository
import com.donghanx.data.repository.favorites.OfflineFirstFavoritesRepository
import com.donghanx.data.repository.setdetails.OfflineFirstSetDetailsRepository
import com.donghanx.data.repository.setdetails.SetDetailsRepository
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

    @Binds
    fun bindFavoritesRepository(
        offlineFirstFavoritesRepository: OfflineFirstFavoritesRepository
    ): FavoritesRepository

    @Binds
    fun bindSetDetailsRepository(
        offlineFirstSetDetailsRepository: OfflineFirstSetDetailsRepository
    ): SetDetailsRepository
}
