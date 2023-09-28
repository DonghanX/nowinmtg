package com.donghanx.nowinmtg

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.donghanx.common.Result
import com.donghanx.common.asResultFlow
import com.donghanx.data.repository.cards.CardsRepository
import com.donghanx.model.Card
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
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
            .flatMapLatest { result ->
                when (result) {
                    is Result.Success -> flowOf(DefaultCardsUiState.Success(result.data))
                    is Result.Error -> flowOf(DefaultCardsUiState.Error(result.exception))
                }
            }
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
