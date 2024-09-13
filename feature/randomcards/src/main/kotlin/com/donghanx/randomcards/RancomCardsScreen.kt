package com.donghanx.randomcards

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.donghanx.mock.MockUtils
import com.donghanx.randomcards.navigation.RANDOM_CARDS_ROUTE
import com.donghanx.ui.CardsGallery

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun RandomCardsScreen(
    onCardClick: (cacheKey: String, multiverseId: Int) -> Unit,
    onShowSnackbar: suspend (message: String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    modifier: Modifier = Modifier,
    viewModel: RandomCardsViewModel = hiltViewModel(),
) {
    val randomCardsUiState by viewModel.randomCardsUiState.collectAsStateWithLifecycle()
    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        isRefreshing = randomCardsUiState.refreshing,
        onRefresh = viewModel::refreshRandomCards,
    ) {
        when (val uiState = randomCardsUiState) {
            is RandomCardsUiState.Success -> {
                CardsGallery(
                    parentRoute = RANDOM_CARDS_ROUTE,
                    cards = uiState.cards,
                    onCardClick = { index, card -> onCardClick(index, card.id.toInt()) },
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedContentScope,
                )
            }
            // TODO: add a placeholder composable for empty cards
            is RandomCardsUiState.Empty -> Unit
        }

        LaunchedEffect(randomCardsUiState.errorMessage) {
            if (randomCardsUiState.hasError()) {
                onShowSnackbar(randomCardsUiState.errorMessage())
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
private fun CardsGalleryPreview() {
    SharedTransitionLayout {
        AnimatedVisibility(visible = true) {
            CardsGallery(
                parentRoute = RANDOM_CARDS_ROUTE,
                cards = MockUtils.emptyCards,
                onCardClick = { _, _ -> },
                sharedTransitionScope = this@SharedTransitionLayout,
                animatedVisibilityScope = this@AnimatedVisibility,
            )
        }
    }
}
