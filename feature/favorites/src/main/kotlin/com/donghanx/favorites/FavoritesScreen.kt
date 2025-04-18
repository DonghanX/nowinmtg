package com.donghanx.favorites

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

    when (val uiState = favoritesUiState) {
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
    }
}
