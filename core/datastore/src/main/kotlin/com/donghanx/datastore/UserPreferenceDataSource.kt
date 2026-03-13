package com.donghanx.datastore

import androidx.datastore.core.DataStore
import com.donghanx.model.DarkModeConfig
import com.donghanx.model.ThemeConfig
import com.donghanx.model.UserPreference
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class UserPreferenceDataSource
@Inject
constructor(private val userPreferenceDataStore: DataStore<UserPreference>) {

    val userPreference: Flow<UserPreference> = userPreferenceDataStore.data

    suspend fun updateThemeConfig(themeConfig: ThemeConfig) {
        userPreferenceDataStore.updateData { it.copy(themeConfig = themeConfig) }
    }

    suspend fun updateDarkModeConfig(darkModeConfig: DarkModeConfig) {
        userPreferenceDataStore.updateData { it.copy(darkModeConfig = darkModeConfig) }
    }
}
