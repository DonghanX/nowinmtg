package com.donghanx.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.donghanx.common.DEFAULT_STOP_TIME_MILLIS
import com.donghanx.data.repository.favorites.FavoritesRepository
import com.donghanx.model.CardPreview
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
internal class FavoritesViewModel @Inject constructor(favoritesRepository: FavoritesRepository) :
    ViewModel() {

    val favoritesUiState: StateFlow<FavoritesUiState> =
        favoritesRepository
            .getFavoriteCards()
            .map { favoriteCards ->
                if (favoriteCards.isEmpty()) FavoritesUiState.Empty
                else FavoritesUiState.Success(favoriteCards.toImmutableList())
            }
            .stateIn(
                scope = viewModelScope,
                started =
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = DEFAULT_STOP_TIME_MILLIS),
                initialValue = FavoritesUiState.Empty,
            )
}

internal sealed interface FavoritesUiState {

    data object Empty : FavoritesUiState

    data object Loading : FavoritesUiState

    data class Success(val favoriteCards: ImmutableList<CardPreview>) : FavoritesUiState
}
