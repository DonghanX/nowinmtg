package com.donghanx.randomcards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.donghanx.design.ui.pullrefresh.PullRefreshIndicator
import com.donghanx.design.ui.pullrefresh.pullRefresh
import com.donghanx.design.ui.pullrefresh.rememberPullRefreshState
import com.donghanx.mock.MockUtils
import com.donghanx.ui.CardsGallery

@Composable
internal fun RandomCardsScreen(
    onCardClick: (cardId: String) -> Unit,
    onShowSnackbar: suspend (message: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RandomCardsViewModel = hiltViewModel(),
) {
    val randomCardsUiState by viewModel.randomCardsUiState.collectAsStateWithLifecycle()
    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = randomCardsUiState.refreshing,
            onRefresh = { viewModel.refreshRandomCards() },
        )

    Box(
        modifier = modifier.fillMaxSize().pullRefresh(state = pullRefreshState),
        contentAlignment = Alignment.Center,
    ) {
        when (val uiState = randomCardsUiState) {
            is RandomCardsUiState.Success -> {
                CardsGallery(uiState.cards, onCardClick = onCardClick)
            }
            // TODO: add a placeholder composable for empty cards
            is RandomCardsUiState.Empty -> Unit
        }

        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = randomCardsUiState.refreshing,
            state = pullRefreshState,
        )

        LaunchedEffect(randomCardsUiState.errorMessage) {
            if (randomCardsUiState.hasError()) {
                onShowSnackbar(randomCardsUiState.errorMessage())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CardsGalleryPreview() {
    CardsGallery(cards = MockUtils.emptyCards, onCardClick = {})
}
