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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
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

    private val _setDetailsUiState = MutableStateFlow(SetDetailsUiState())
    val setDetailsUiState: StateFlow<SetDetailsUiState> = _setDetailsUiState.asStateFlow()

    init {
        // Load cached cards first (offline‑first), then refresh from network when available.
        observeSetDetails()
        refreshCardInSets()
    }

    private fun observeSetDetails() {
        viewModelScope.launch {
            setInfoFlow
                .filterNotNull()
                .flatMapLatest { setInfo ->
                    setDetailsRepository.getCardsInCurrentSet(setInfo.code).map { cardsInSet ->
                        SetInfoAndCards(setInfo, cardsInSet)
                    }
                }
                .filter { it.cardsInSet.isNotEmpty() }
                .onEach { (setInfo, cardsInSet) ->
                    _setDetailsUiState.update {
                        SetDetailsUiState(
                            setInfo = setInfo,
                            cards = cardsInSet.toImmutableList(),
                            isLoading = false,
                            errorMessage = it.errorMessage,
                        )
                    }
                }
                .collect()
        }
    }

    private fun refreshCardInSets() {
        viewModelScope.launch {
            setInfoFlow
                .filterNotNull()
                .flatMapLatest { setInfo ->
                    setDetailsRepository.refreshCardsInCurrentSet(searchUri = setInfo.searchUri)
                }
                .filterIsInstance<NetworkResult.Error>()
                .onEach { error ->
                    _setDetailsUiState.update {
                        val errorMessage = error.exception.asErrorMessage()
                        it.copy(isLoading = false, errorMessage = errorMessage)
                    }
                }
                .collect()
        }
    }
}

data class SetInfoAndCards(val setInfo: SetInfo, val cardsInSet: List<CardPreview>)

data class SetDetailsUiState(
    val cards: ImmutableList<CardPreview> = persistentListOf(),
    val setInfo: SetInfo? = null,
    val isLoading: Boolean = true,
    val errorMessage: ErrorMessage = emptyErrorMessage(),
)
