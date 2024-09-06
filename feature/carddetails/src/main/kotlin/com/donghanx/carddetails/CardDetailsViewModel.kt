package com.donghanx.carddetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.donghanx.carddetails.navigation.CARD_ID_ARGS
import com.donghanx.carddetails.navigation.MULTIVERSE_ID_ARGS
import com.donghanx.common.ErrorMessage
import com.donghanx.common.INVALID_ID
import com.donghanx.common.NetworkResult
import com.donghanx.common.asErrorMessage
import com.donghanx.common.emptyErrorMessage
import com.donghanx.data.repository.carddetails.CardDetailsRepository
import com.donghanx.data.repository.favorites.FavoritesRepository
import com.donghanx.domain.ObserveCardDetailsUseCase
import com.donghanx.domain.ObserveIsCardFavoriteUseCase
import com.donghanx.domain.RefreshCardDetailsUseCase
import com.donghanx.model.CardDetails
import com.donghanx.model.Ruling
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
internal class CardDetailsViewModel
@Inject
constructor(
    private val favoritesRepository: FavoritesRepository,
    private val cardDetailsRepository: CardDetailsRepository,
    private val refreshCardDetailsUseCase: RefreshCardDetailsUseCase,
    private val observeCardDetailsUseCase: ObserveCardDetailsUseCase,
    observeIsCardFavoriteUseCase: ObserveIsCardFavoriteUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val cardIdFlow: StateFlow<String?> =
        savedStateHandle.getStateFlow(key = CARD_ID_ARGS, initialValue = null)
    private val multiverseIdFlow: StateFlow<Int> =
        savedStateHandle.getStateFlow(key = MULTIVERSE_ID_ARGS, initialValue = INVALID_ID)

    private val viewModelState = MutableStateFlow(CardDetailsViewModelState(refreshing = true))

    val cardDetailsUiState: StateFlow<CardDetailsUiState> =
        viewModelState
            .map(CardDetailsViewModelState::toUiState)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(DEFAULT_STOP_TIME_MILLIS),
                initialValue = viewModelState.value.toUiState(),
            )

    val isCardFavorite: StateFlow<Boolean> =
        observeIsCardFavoriteUseCase(cardIdFlow, multiverseIdFlow)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(DEFAULT_STOP_TIME_MILLIS),
                initialValue = false,
            )

    init {
        refreshCardDetails()
        observeCardDetails()
    }

    private fun observeCardDetails() {
        viewModelScope.launch {
            observeCardDetailsUseCase(cardIdFlow = cardIdFlow, multiverseIdFlow = multiverseIdFlow)
                .onEach { (cardDetails, rulings) ->
                    viewModelState.update {
                        it.copy(cardDetails = cardDetails, rulings = rulings, refreshing = false)
                    }
                }
                .collect()
        }
    }

    fun refreshCardDetails() {
        viewModelState.update { it.copy(refreshing = true) }

        viewModelScope.launch {
            refreshCardDetailsUseCase(cardIdFlow, multiverseIdFlow)
                .onEach { it.updateViewModelState() }
                .collect()
        }
    }

    fun onFavoriteClick() {
        viewModelScope.launch {
            viewModelState.value.cardDetails?.let { cardDetails ->
                favoritesRepository.favoriteCardOrUndo(cardDetails)
            }
        }
    }

    private fun <T> NetworkResult<T>.updateViewModelState() {
        viewModelState.update { prevState ->
            when (this) {
                is NetworkResult.Success -> {
                    prevState.copy(refreshing = false, errorMessage = emptyErrorMessage())
                }
                is NetworkResult.Error ->
                    prevState.copy(
                        refreshing = false,
                        errorMessage = exception.asErrorMessage(id = prevState.errorMessage.id + 1),
                    )
            }
        }
    }
}

internal sealed interface CardDetailsUiState {
    val refreshing: Boolean
    val errorMessage: ErrorMessage

    fun hasError(): Boolean = errorMessage.hasError

    data class Success(
        val cardDetails: CardDetails,
        val rulings: List<Ruling>,
        override val refreshing: Boolean,
        override val errorMessage: ErrorMessage = emptyErrorMessage(),
    ) : CardDetailsUiState

    data class NoCardDetails(
        override val refreshing: Boolean,
        override val errorMessage: ErrorMessage = emptyErrorMessage(),
    ) : CardDetailsUiState
}

private data class CardDetailsViewModelState(
    val cardDetails: CardDetails? = null,
    val rulings: List<Ruling> = emptyList(),
    val refreshing: Boolean,
    val errorMessage: ErrorMessage = emptyErrorMessage(),
) {
    fun toUiState(): CardDetailsUiState =
        when {
            cardDetails != null ->
                CardDetailsUiState.Success(
                    cardDetails = cardDetails,
                    rulings = rulings,
                    refreshing = refreshing,
                    errorMessage = errorMessage,
                )
            else ->
                CardDetailsUiState.NoCardDetails(
                    refreshing = refreshing,
                    errorMessage = errorMessage,
                )
        }
}

private const val DEFAULT_STOP_TIME_MILLIS = 5_000L
