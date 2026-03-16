package com.donghanx.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.donghanx.common.DEFAULT_STOP_TIME_MILLIS
import com.donghanx.data.repository.userpreference.UserPreferenceRepository
import com.donghanx.model.ContrastLevel
import com.donghanx.model.DarkModeConfig
import com.donghanx.model.ThemeConfig
import com.donghanx.model.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel
@Inject
constructor(private val userPreferenceRepository: UserPreferenceRepository) : ViewModel() {

    val settingsUiState: StateFlow<SettingsUiState> =
        userPreferenceRepository.userPreference
            .map(SettingsUiState::Success)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(DEFAULT_STOP_TIME_MILLIS),
                initialValue = SettingsUiState.Loading,
            )

    fun updateThemeConfig(themeConfig: ThemeConfig) {
        viewModelScope.launch { userPreferenceRepository.updateThemeConfig(themeConfig) }
    }

    fun updateDarkModeConfig(darkModeConfig: DarkModeConfig) {
        viewModelScope.launch { userPreferenceRepository.updateDarkModeConfig(darkModeConfig) }
    }

    fun updateContrastLevel(contrastLevel: ContrastLevel) {
        viewModelScope.launch { userPreferenceRepository.updateContrastLevel(contrastLevel) }
    }
}

sealed interface SettingsUiState {
    data object Loading : SettingsUiState

    data class Success(val userPreference: UserPreference) : SettingsUiState
}
