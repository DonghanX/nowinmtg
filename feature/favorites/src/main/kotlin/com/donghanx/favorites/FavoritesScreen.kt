package com.donghanx.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.donghanx.design.ui.placeholder.EmptyScreenWithIcon
import com.donghanx.favorites.navigation.FavoritesRoute
import com.donghanx.model.CardPreview
import com.donghanx.ui.CardsGallery

@Composable
internal fun FavoritesScreen(
    onCardClick: (card: CardPreview) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel(),
) {
    val favoritesUiState by viewModel.favoritesUiState.collectAsStateWithLifecycle()

    FavoritesScreen(uiState = favoritesUiState, onCardClick = onCardClick, modifier = modifier)
}

@Composable
private fun FavoritesScreen(
    uiState: FavoritesUiState,
    onCardClick: (card: CardPreview) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is FavoritesUiState.Success ->
                CardsGallery(
                    parentRoute = FavoritesRoute.toString(),
                    cards = uiState.favoriteCards,
                    onCardClick = onCardClick,
                    modifier = modifier,
                )
            is FavoritesUiState.Empty ->
                EmptyScreenWithIcon(
                    text = stringResource(R.string.no_favorite_cards),
                    imageVector = Icons.Outlined.FavoriteBorder,
                )
            is FavoritesUiState.Loading ->
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
