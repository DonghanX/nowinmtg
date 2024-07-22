package com.donghanx.randomcards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.donghanx.common.ErrorMessage
import com.donghanx.common.NetworkResult
import com.donghanx.common.asErrorMessage
import com.donghanx.common.emptyErrorMessage
import com.donghanx.data.repository.cards.RandomCardsRepository
import com.donghanx.model.CardPreview
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
internal class RandomCardsViewModel
@Inject
constructor(
    private val randomCardsRepository: RandomCardsRepository,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(RandomCardsViewModelState(refreshing = true))

    val randomCardsUiState: StateFlow<RandomCardsUiState> =
        viewModelState
            .map(RandomCardsViewModelState::toUiState)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(DEFAULT_STOP_TIME_MILLIS),
                initialValue = viewModelState.value.toUiState()
            )

    init {
        observeRandomCards()

        viewModelScope.launch {
            if (randomCardsRepository.shouldFetchInitialCards()) {
                refreshRandomCards()
            }
        }
    }

    fun refreshRandomCards() {
        viewModelState.update { it.copy(refreshing = true) }

        viewModelScope.launch {
            randomCardsRepository
                .refreshRandomCards(shouldContainImageUrl = true)
                .onEach { it.updateViewModelState() }
                .collect()
        }
    }

    private fun observeRandomCards() {
        viewModelScope.launch {
            randomCardsRepository
                .getRandomCards()
                .onEach { randomCards ->
                    viewModelState.update { it.copy(randomCards = randomCards, refreshing = false) }
                }
                .collect()
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

internal sealed interface RandomCardsUiState {
    val refreshing: Boolean
    val errorMessage: ErrorMessage

    fun hasError(): Boolean = errorMessage.hasError

    data class Success(
        val cards: List<CardPreview>,
        override val refreshing: Boolean,
        override val errorMessage: ErrorMessage = emptyErrorMessage()
    ) : RandomCardsUiState

    data class Empty(
        override val refreshing: Boolean,
        override val errorMessage: ErrorMessage = emptyErrorMessage()
    ) : RandomCardsUiState
}

private data class RandomCardsViewModelState(
    val randomCards: List<CardPreview> = emptyList(),
    val refreshing: Boolean,
    val errorMessage: ErrorMessage = emptyErrorMessage()
) {
    fun toUiState(): RandomCardsUiState =
        when {
            randomCards.isNotEmpty() ->
                RandomCardsUiState.Success(
                    cards = randomCards,
                    refreshing = refreshing,
                    errorMessage = errorMessage
                )
            else -> RandomCardsUiState.Empty(refreshing = refreshing, errorMessage = errorMessage)
        }
}

private const val DEFAULT_STOP_TIME_MILLIS = 5_000L
