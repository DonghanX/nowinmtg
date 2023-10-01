package com.donghanx.data.di

import com.donghanx.data.repository.cards.CardsRepository
import com.donghanx.data.repository.cards.RandomCardsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds fun bindCardsRepository(remoteCardsRepository: RandomCardsRepository): CardsRepository
}
