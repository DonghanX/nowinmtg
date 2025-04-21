package com.donghanx.setdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.donghanx.common.DEFAULT_STOP_TIME_MILLIS
import com.donghanx.common.ErrorMessage
import com.donghanx.common.NetworkResult
import com.donghanx.common.asErrorMessage
import com.donghanx.common.emptyErrorMessage
import com.donghanx.data.repository.setdetails.SetDetailsRepository
import com.donghanx.data.repository.sets.SetsRepository
import com.donghanx.model.CardPreview
import com.donghanx.model.SetInfo
import com.donghanx.setdetails.navigation.SetDetailsRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class SetDetailsViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
    setsRepository: SetsRepository,
    private val setDetailsRepository: SetDetailsRepository,
) : ViewModel() {

    private val setDetailsRoute = savedStateHandle.toRoute<SetDetailsRoute>()

    private val setInfoFlow =
        setsRepository
            .getSetInfoById(setDetailsRoute.setId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(DEFAULT_STOP_TIME_MILLIS),
                initialValue = null,
            )

    private val localSetDetailsUiStateFlow =
        setInfoFlow
            .filterNotNull()
            .flatMapLatest { setInfo ->
                setDetailsRepository.getCardsInCurrentSet(setInfo.code).map { cardsInSet ->
                    SetInfoAndCards(setInfo, cardsInSet)
                }
            }
            .filter { it.cardsInSet.isNotEmpty() }
            .map { (setInfo, cardsInSet) ->
                SetDetailsUiState(
                    setInfo = setInfo,
                    cards = cardsInSet.toImmutableList(),
                    isLoading = false,
                )
            }

    private val refreshErrorStateFlow =
        setInfoFlow
            .filterNotNull()
            .flatMapLatest { setInfo ->
                setDetailsRepository.refreshCardsInCurrentSet(searchUri = setInfo.searchUri)
            }
            .filterIsInstance<NetworkResult.Error>()
            .map {
                SetDetailsUiState(isLoading = false, errorMessage = it.exception.asErrorMessage())
            }

    // Load cached cards first (offline‑first), then refresh from network when available.
    val setDetailsUiStateFlow =
        merge(localSetDetailsUiStateFlow, refreshErrorStateFlow)
            .scan(SetDetailsUiState()) { accState, newState ->
                val errorMessage = newState.errorMessage

                if (newState.errorMessage.hasError)
                    accState.copy(errorMessage = errorMessage.copy(id = errorMessage.id + 1))
                else newState
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(DEFAULT_STOP_TIME_MILLIS),
                initialValue = SetDetailsUiState(),
            )
}

data class SetInfoAndCards(val setInfo: SetInfo, val cardsInSet: List<CardPreview>)

data class SetDetailsUiState(
    val cards: ImmutableList<CardPreview> = persistentListOf(),
    val setInfo: SetInfo? = null,
    val isLoading: Boolean = true,
    val errorMessage: ErrorMessage = emptyErrorMessage(),
)
