package com.donghanx.data.repository.userpreference

import com.donghanx.datastore.UserPreferenceDataSource
import com.donghanx.model.ContrastLevel
import com.donghanx.model.DarkModeConfig
import com.donghanx.model.ThemeConfig
import com.donghanx.model.UserPreference
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class OfflineFirstUserPreferenceRepository
@Inject
constructor(
    private val userPreferenceDataSource: UserPreferenceDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : UserPreferenceRepository {

    override val userPreference: Flow<UserPreference> = userPreferenceDataSource.userPreference

    override suspend fun updateThemeConfig(themeConfig: ThemeConfig) =
        withContext(ioDispatcher) { userPreferenceDataSource.updateThemeConfig(themeConfig) }

    override suspend fun updateDarkModeConfig(darkModeConfig: DarkModeConfig) =
        withContext(ioDispatcher) { userPreferenceDataSource.updateDarkModeConfig(darkModeConfig) }

    override suspend fun updateContrastLevel(contrastLevel: ContrastLevel) {
        withContext(ioDispatcher) { userPreferenceDataSource.updateContrastLevel(contrastLevel) }
    }
}
