package com.donghanx.favorites

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.donghanx.ui.CardsGallery

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun FavoritesScreen(
    onCardClick: (cardId: String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
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
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope,
            )
        is FavoritesUiState.Empty -> EmptyFavoritesView()
    }
}
