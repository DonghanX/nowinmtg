package com.donghanx.carddetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.donghanx.carddetails.navigation.CARD_ID_ARGS
import com.donghanx.common.ErrorMessage
import com.donghanx.common.NetworkResult
import com.donghanx.common.asErrorMessage
import com.donghanx.common.emptyErrorMessage
import com.donghanx.data.repository.carddetails.CardDetailsRepository
import com.donghanx.data.repository.favorites.FavoritesRepository
import com.donghanx.model.CardDetails
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
class CardDetailsViewModel
@Inject
constructor(
    private val cardDetailsRepository: CardDetailsRepository,
    private val favoritesRepository: FavoritesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val cardId: StateFlow<String?> =
        savedStateHandle.getStateFlow(key = CARD_ID_ARGS, initialValue = null)

    private val validCardId: Flow<String> = cardId.filterNotNull()

    private val viewModelState = MutableStateFlow(CardDetailsViewModelState(refreshing = true))

    val cardDetailsUiState: StateFlow<CardDetailsUiState> =
        viewModelState
            .map(CardDetailsViewModelState::toUiState)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(DEFAULT_STOP_TIME_MILLIS),
                initialValue = viewModelState.value.toUiState()
            )

    init {
        observeCardDetails()
    }

    private fun observeCardDetails() {
        refreshCardDetails()

        viewModelScope.launch {
            validCardId
                .flatMapLatest { cardId ->
                    // Populates the card details using the offline-stored data if exists
                    cardDetailsRepository.getCardDetailsById(cardId)
                }
                .onEach { cardDetails ->
                    viewModelState.update { it.copy(cardDetails = cardDetails, refreshing = false) }
                }
                .collect()
        }
    }

    fun refreshCardDetails() {
        viewModelState.update { it.copy(refreshing = true) }

        viewModelScope.launch {
            validCardId
                .flatMapLatest { cardId -> cardDetailsRepository.refreshCardDetails(cardId) }
                .onEach { it.updateViewModelState() }
                .collect()
        }
    }

    fun onFavorites() {
        viewModelScope.launch {
            viewModelState.value.cardDetails?.let { cardDetails ->
                favoritesRepository.favoriteCard(cardDetails)
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
                        errorMessage = exception.asErrorMessage(id = prevState.errorMessage.id + 1)
                    )
            }
        }
    }
}

sealed interface CardDetailsUiState {
    val refreshing: Boolean
    val errorMessage: ErrorMessage

    data class Success(
        val cardDetails: CardDetails,
        override val refreshing: Boolean,
        override val errorMessage: ErrorMessage = emptyErrorMessage()
    ) : CardDetailsUiState

    data class NoCardDetails(
        override val refreshing: Boolean,
        override val errorMessage: ErrorMessage = emptyErrorMessage()
    ) : CardDetailsUiState
}

private data class CardDetailsViewModelState(
    val cardDetails: CardDetails? = null,
    val refreshing: Boolean,
    val errorMessage: ErrorMessage = emptyErrorMessage()
) {
    fun toUiState(): CardDetailsUiState =
        when {
            cardDetails != null ->
                CardDetailsUiState.Success(
                    cardDetails = cardDetails,
                    refreshing = refreshing,
                    errorMessage = errorMessage
                )
            else ->
                CardDetailsUiState.NoCardDetails(
                    refreshing = refreshing,
                    errorMessage = errorMessage
                )
        }
}

fun CardDetailsUiState.hasError(): Boolean = errorMessage.hasError

private const val DEFAULT_STOP_TIME_MILLIS = 5_000L
