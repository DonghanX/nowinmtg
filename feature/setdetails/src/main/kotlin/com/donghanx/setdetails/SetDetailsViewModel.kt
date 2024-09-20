package com.donghanx.setdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.donghanx.setdetails.navigation.SET_CODE_ARGS
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class SetDetailsViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle) :
    ViewModel() {

    private val setCodeFlow: StateFlow<String?> =
        savedStateHandle.getStateFlow(key = SET_CODE_ARGS, initialValue = null)

    val setCode =
        setCodeFlow.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null,
        )
}
