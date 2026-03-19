package com.donghanx.datastore.di

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.donghanx.datastore.UserPreferenceSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private const val USER_PREFERENCE_DATA_STORE_FILE_NAME = "user_preference.json"

    @Provides
    @Singleton
    fun provideUserDataDataStore(@ApplicationContext context: Context) =
        DataStoreFactory.create(
            serializer = UserPreferenceSerializer,
            produceFile = { context.dataStoreFile(USER_PREFERENCE_DATA_STORE_FILE_NAME) },
        )
}
