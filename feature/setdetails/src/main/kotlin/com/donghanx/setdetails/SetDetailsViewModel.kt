package com.donghanx.setdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.donghanx.data.repository.sets.SetsRepository
import com.donghanx.model.SetInfo
import com.donghanx.setdetails.navigation.SET_ID_ARGS
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class SetDetailsViewModel
@Inject
constructor(private val savedStateHandle: SavedStateHandle, setsRepository: SetsRepository) :
    ViewModel() {

    private val setIdFlow: StateFlow<String?> =
        savedStateHandle.getStateFlow(key = SET_ID_ARGS, initialValue = null)

    val setInfoFlow: StateFlow<SetInfo?> =
        setIdFlow
            .filterNotNull()
            .flatMapLatest { id -> setsRepository.getSetInfoById(id) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
                initialValue = null,
            )
}
