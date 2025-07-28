package com.donghanx.setdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.donghanx.common.DEFAULT_STOP_TIME_MILLIS
import com.donghanx.common.ErrorMessage
import com.donghanx.common.emptyErrorMessage
import com.donghanx.domain.ObserveSetDetailsUseCase
import com.donghanx.domain.SetDetailsUseCaseResult
import com.donghanx.model.CardPreview
import com.donghanx.model.SetInfo
import com.donghanx.setdetails.navigation.SetDetailsRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class SetDetailsViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
    observeSetDetailsUseCase: ObserveSetDetailsUseCase,
) : ViewModel() {

    private val setDetailsRoute = savedStateHandle.toRoute<SetDetailsRoute>()

    /** Load cached cards first (offline‑first), then refresh from network when available. */
    val setDetailsUiStateFlow =
        observeSetDetailsUseCase(setDetailsRoute.setId)
            .scan(SetDetailsUiState()) { uiState, useCaseState ->
                val errorMessage = useCaseState.errorMessage

                if (errorMessage.hasError) {
                    uiState.copy(errorMessage = errorMessage.copy(id = errorMessage.id + 1))
                } else {
                    useCaseState.toUiState()
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(DEFAULT_STOP_TIME_MILLIS),
                initialValue = SetDetailsUiState(),
            )
}

data class SetDetailsUiState(
    val cards: ImmutableList<CardPreview> = persistentListOf(),
    val setInfo: SetInfo? = null,
    val isLoading: Boolean = true,
    val errorMessage: ErrorMessage = emptyErrorMessage(),
)

private fun SetDetailsUseCaseResult.toUiState(): SetDetailsUiState =
    SetDetailsUiState(
        setInfo = setInfo,
        cards = cards.toPersistentList(),
        isLoading = false,
        errorMessage = errorMessage,
    )
