package com.donghanx.favorites

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.donghanx.favorites.navigation.FAVORITES_ROUTE
import com.donghanx.model.CardPreview
import com.donghanx.ui.CardsGallery

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun FavoritesScreen(
    onCardClick: (cacheKey: String, card: CardPreview) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel(),
) {
    val favoritesUiState by viewModel.favoritesUiState.collectAsStateWithLifecycle()

    when (val uiState = favoritesUiState) {
        is FavoritesUiState.Success ->
            CardsGallery(
                parentRoute = FAVORITES_ROUTE,
                cards = uiState.favoriteCards,
                onCardClick = onCardClick,
                modifier = modifier,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedContentScope,
            )
        is FavoritesUiState.Empty -> EmptyFavoritesView()
    }
}
