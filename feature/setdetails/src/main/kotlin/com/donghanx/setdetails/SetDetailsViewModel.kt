package com.donghanx.setdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.donghanx.common.ErrorMessage
import com.donghanx.common.emptyErrorMessage
import com.donghanx.data.repository.setdetails.SetDetailsRepository
import com.donghanx.data.repository.sets.SetsRepository
import com.donghanx.model.CardPreview
import com.donghanx.model.SetInfo
import com.donghanx.setdetails.navigation.SET_ID_ARGS
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SetDetailsViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
    private val setsRepository: SetsRepository,
    private val setDetailsRepository: SetDetailsRepository,
) : ViewModel() {

    private val setIdFlow: StateFlow<String?> =
        savedStateHandle.getStateFlow(key = SET_ID_ARGS, initialValue = null)

    private val setInfoFlow: Flow<SetInfo> =
        setIdFlow.filterNotNull().flatMapLatest { id -> setsRepository.getSetInfoById(id) }

    private val viewModelState = MutableStateFlow(SetDetailsViewModelState(refreshing = true))

    val setDetailsUiState: StateFlow<SetDetailsUiState> =
        viewModelState
            .map(SetDetailsViewModelState::toUiState)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = viewModelState.value.toUiState(),
            )

    init {
        observeSetDetails()
        refreshCardInSets()
    }

    private fun observeSetDetails() {
        // TODO: create a UseCase to encapsulate the following code snippet
        viewModelScope.launch {
            setIdFlow
                .filterNotNull()
                .flatMapLatest { setId ->
                    setsRepository.getSetInfoById(setId).flatMapLatest { setInfo ->
                        setDetailsRepository.getCardsInCurrentSet(setInfo.code).map { cardsInSet ->
                            SetInfoAndCards(setInfo, cardsInSet)
                        }
                    }
                }
                .onEach { (setInfo, cardsInSet) ->
                    viewModelState.update { it.copy(setInfo = setInfo, cards = cardsInSet) }
                }
                .collect()
        }
    }

    private fun refreshCardInSets() {
        viewModelScope.launch {
            setInfoFlow
                .flatMapLatest { setInfo ->
                    setDetailsRepository.refreshCardsInCurrentSet(searchUri = setInfo.searchUri)
                }
                .collect()
        }
    }
}

data class SetInfoAndCards(val setInfo: SetInfo, val cardsInSet: List<CardPreview>)

sealed interface SetDetailsUiState {
    val setInfo: SetInfo?

    data class Success(val cards: List<CardPreview>, override val setInfo: SetInfo?) :
        SetDetailsUiState

    data class NoSetDetails(
        override val setInfo: SetInfo?,
        val errorMessage: ErrorMessage = emptyErrorMessage(),
    ) : SetDetailsUiState

    data class Loading(override val setInfo: SetInfo? = null) : SetDetailsUiState
}

private data class SetDetailsViewModelState(
    val setInfo: SetInfo? = null,
    val cards: List<CardPreview> = emptyList(),
    val refreshing: Boolean,
    val errorMessage: ErrorMessage = emptyErrorMessage(),
) {
    fun toUiState(): SetDetailsUiState =
        when {
            cards.isNotEmpty() -> SetDetailsUiState.Success(setInfo = setInfo, cards = cards)
            errorMessage.hasError ->
                SetDetailsUiState.NoSetDetails(setInfo = setInfo, errorMessage = errorMessage)
            else -> SetDetailsUiState.Loading(setInfo = setInfo)
        }
}
