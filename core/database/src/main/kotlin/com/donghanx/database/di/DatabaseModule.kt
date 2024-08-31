package com.donghanx.database.di

import android.content.Context
import androidx.room.Room
import com.donghanx.database.NimDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesNimDatabase(@ApplicationContext applicationContext: Context): NimDatabase =
        Room.databaseBuilder(applicationContext, NimDatabase::class.java, "nim-database")
            .fallbackToDestructiveMigration()
            .build()
}
