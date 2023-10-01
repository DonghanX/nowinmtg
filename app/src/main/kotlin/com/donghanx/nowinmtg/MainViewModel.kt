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
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val cardsRepository: CardsRepository,
) : ViewModel() {

    companion object {
        private const val DEFAULT_STOP_TIMEOUT_MILLIS = 5_000L
    }

    init {
        refreshRandomCards()
    }

    val randomCardsUiState: StateFlow<RandomCardsUiState> =
        cardsRepository
            .getRandomCards()
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

    fun refreshRandomCards() {
        viewModelScope.launch { cardsRepository.refreshRandomCards() }
    }
}

sealed interface RandomCardsUiState {
    data class Success(val cards: List<Card>) : RandomCardsUiState

    data class Error(val exception: Throwable?) : RandomCardsUiState

    data object Loading : RandomCardsUiState

    data object Refreshing : RandomCardsUiState
}
