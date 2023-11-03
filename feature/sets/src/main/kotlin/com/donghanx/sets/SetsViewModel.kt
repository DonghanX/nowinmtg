package com.donghanx.sets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.donghanx.common.ErrorMessage
import com.donghanx.common.NetworkResult
import com.donghanx.common.asErrorMessage
import com.donghanx.common.emptyErrorMessage
import com.donghanx.data.repository.sets.SetsRepository
import com.donghanx.model.SetInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SetsViewModel @Inject constructor(private val setsRepository: SetsRepository) : ViewModel() {

    private val viewModelState = MutableStateFlow(SetsViewModelState(refreshing = false))

    val setsUiState: StateFlow<SetsUiState> =
        viewModelState
            .map(SetsViewModelState::toUiState)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(DEFAULT_STOP_TIME_MILLIS),
                initialValue = viewModelState.value.toUiState()
            )

    init {
        observeSets()

        viewModelScope.launch {
            if (setsRepository.shouldFetchInitialSets()) {
                refreshSets()
            }
        }
    }

    private fun observeSets() {
        viewModelScope.launch {
            setsRepository
                .getAllSets()
                .onEach { sets ->
                    viewModelState.update { it.copy(sets = sets, refreshing = false) }
                }
                .collect()
        }
    }

    fun refreshSets() {
        viewModelScope.launch {
            viewModelState.update { it.copy(refreshing = true) }

            setsRepository.refreshAllSets().onEach { it.updateViewModelState() }.collect()
        }
    }

    private fun <T> NetworkResult<T>.updateViewModelState() {
        viewModelState.update { prevState ->
            when (this) {
                is NetworkResult.Success ->
                    prevState.copy(refreshing = false, errorMessage = emptyErrorMessage())
                is NetworkResult.Error ->
                    prevState.copy(
                        refreshing = false,
                        errorMessage = exception.asErrorMessage(id = prevState.errorMessage.id + 1)
                    )
            }
        }
    }
}

sealed interface SetsUiState {
    val refreshing: Boolean
    val errorMessage: ErrorMessage

    data class Success(
        val sets: List<SetInfo>,
        override val refreshing: Boolean,
        override val errorMessage: ErrorMessage = emptyErrorMessage()
    ) : SetsUiState

    data class Empty(
        override val refreshing: Boolean,
        override val errorMessage: ErrorMessage = emptyErrorMessage()
    ) : SetsUiState
}

private data class SetsViewModelState(
    val sets: List<SetInfo> = emptyList(),
    val refreshing: Boolean,
    val errorMessage: ErrorMessage = emptyErrorMessage()
) {
    fun toUiState(): SetsUiState =
        when {
            sets.isNotEmpty() ->
                SetsUiState.Success(
                    sets = sets,
                    refreshing = refreshing,
                    errorMessage = errorMessage
                )
            else -> SetsUiState.Empty(refreshing = refreshing, errorMessage = errorMessage)
        }
}

fun SetsUiState.hasError(): Boolean = errorMessage.hasError

private const val DEFAULT_STOP_TIME_MILLIS = 5_000L
