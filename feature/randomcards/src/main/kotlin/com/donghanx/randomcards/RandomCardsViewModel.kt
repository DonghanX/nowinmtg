package com.donghanx.randomcards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.donghanx.common.NetworkResult
import com.donghanx.common.NetworkStatus
import com.donghanx.data.repository.cards.CardsRepository
import com.donghanx.model.Card
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
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

    private val _refreshing = MutableStateFlow(false)
    val refreshing = _refreshing.asStateFlow()

    private val _networkStatus = MutableStateFlow(networkSuccess())
    val networkStatus = _networkStatus.asStateFlow()

    val randomCardsUiState: StateFlow<RandomCardsUiState> =
        cardsRepository
            .getRandomCards()
            .mapLatest { cards ->
                when {
                    cards.isNotEmpty() -> RandomCardsUiState.Success(cards)
                    else -> RandomCardsUiState.Empty
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(DEFAULT_STOP_TIMEOUT_MILLIS),
                initialValue = RandomCardsUiState.Loading
            )

    init {
        viewModelScope.launch {
            if (cardsRepository.shouldFetchInitialCards()) {
                refreshRandomCards()
            }
        }
    }

    fun refreshRandomCards() {
        viewModelScope.launch {
            withRefreshing {
                cardsRepository
                    .refreshRandomCards(shouldContainImageUrl = true)
                    .onEach { networkResult -> networkResult.updateNetworkStatus() }
                    .collect()
            }
        }
    }

    private inline fun withRefreshing(block: () -> Unit) {
        _refreshing.update { true }
        block()
        _refreshing.update { false }
    }

    private fun <T> NetworkResult<T>.updateNetworkStatus() {
        when (this) {
            is NetworkResult.Success -> {
                _networkStatus.update { networkSuccess() }
            }
            is NetworkResult.Error -> {
                _networkStatus.update {
                    it.copy(
                        hasError = true,
                        errorMessage = exception?.message.orEmpty(),
                        replayTick = it.replayTick + 1
                    )
                }
            }
        }
    }

    private fun networkSuccess() = NetworkStatus(hasError = false)
}

sealed interface RandomCardsUiState {
    data class Success(val cards: List<Card>) : RandomCardsUiState
    data object Empty : RandomCardsUiState
    data object Loading : RandomCardsUiState
}