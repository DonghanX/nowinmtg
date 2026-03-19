package com.donghanx.nowinmtg

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.donghanx.common.DEFAULT_STOP_TIME_MILLIS
import com.donghanx.data.repository.userpreference.UserPreferenceRepository
import com.donghanx.model.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class MainViewModel @Inject constructor(userPreferenceRepository: UserPreferenceRepository) :
    ViewModel() {

    val userPreference: StateFlow<UserPreference> =
        userPreferenceRepository.userPreference.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(DEFAULT_STOP_TIME_MILLIS),
            initialValue = UserPreference(),
        )
}
