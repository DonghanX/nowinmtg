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
class MainViewModel
@Inject
constructor(
    cardsRepository: CardsRepository,
) : ViewModel() {

    companion object {
        private const val DEFAULT_STOP_TIMEOUT_MILLIS = 5_000L
    }

    val randomCardsUiState: StateFlow<RandomCardsUiState> =
        cardsRepository
            .randomCards()
            .asResultFlow()
            .foldResult(
                onSuccess = { cards -> RandomCardsUiState.Success(cards) },
                onError = { exception -> RandomCardsUiState.Error(exception) }
            )
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(DEFAULT_STOP_TIMEOUT_MILLIS),
                initialValue = RandomCardsUiState.Loading
            )
}

sealed interface RandomCardsUiState {
    data class Success(val cards: List<Card>) : RandomCardsUiState

    data class Error(val exception: Throwable?) : RandomCardsUiState

    data object Loading : RandomCardsUiState

    data object Refreshing : RandomCardsUiState
}
