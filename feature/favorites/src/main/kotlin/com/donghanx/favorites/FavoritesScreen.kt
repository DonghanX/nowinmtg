package com.donghanx.favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.donghanx.ui.CardsGallery

@Composable
internal fun FavoritesScreen(
    onCardClick: (cardId: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel(),
) {
    val favoritesUiState by viewModel.favoritesUiState.collectAsStateWithLifecycle()

    when (val uiState = favoritesUiState) {
        is FavoritesUiState.Success ->
            CardsGallery(
                cards = uiState.favoriteCards,
                onCardClick = { onCardClick(it.id) },
                modifier = modifier,
            )
        is FavoritesUiState.Empty -> EmptyFavoritesView()
    }
}
