package com.donghanx.data.repository.userpreference

import com.donghanx.model.DarkModeConfig
import com.donghanx.model.ThemeConfig
import com.donghanx.model.UserPreference
import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {

    val userPreference: Flow<UserPreference>

    suspend fun updateThemeConfig(themeConfig: ThemeConfig)

    suspend fun updateDarkModeConfig(darkModeConfig: DarkModeConfig)
}
