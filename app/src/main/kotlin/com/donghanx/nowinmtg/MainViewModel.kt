package com.donghanx.nowinmtg

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.donghanx.common.asResultFlow
import com.donghanx.common.foldResult
import com.donghanx.data.repository.cards.CardsRepository
import com.donghanx.model.Card
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class MainViewModel @Inject constructor(private val cardsRepository: CardsRepository) :
    ViewModel() {

    companion object {
        private const val DEFAULT_STOP_TIMEOUT_MILLIS = 5_000L
    }

    val defaultCardsStateFlow: StateFlow<DefaultCardsUiState> =
        cardsRepository
            .defaultCards()
            .asResultFlow()
            .foldResult(
                onSuccess = { cards -> DefaultCardsUiState.Success(cards) },
                onError = { exception -> DefaultCardsUiState.Error(exception) }
            )
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(DEFAULT_STOP_TIMEOUT_MILLIS),
                initialValue = DefaultCardsUiState.Loading
            )
}

sealed interface DefaultCardsUiState {
    data class Success(val cards: List<Card>) : DefaultCardsUiState

    data class Error(val exception: Throwable?) : DefaultCardsUiState

    data object Loading : DefaultCardsUiState
}
