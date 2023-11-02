package com.donghanx.randomcards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.donghanx.design.R as DesignR
import com.donghanx.design.composable.extensions.rippleClickable
import com.donghanx.design.ui.pullrefresh.PullRefreshIndicator
import com.donghanx.design.ui.pullrefresh.pullRefresh
import com.donghanx.design.ui.pullrefresh.rememberPullRefreshState
import com.donghanx.design.ui.scrolltotop.ScrollToTopButton
import com.donghanx.mock.MockUtils
import com.donghanx.model.CardPreview
import kotlinx.coroutines.launch

@Composable
fun RandomCardsScreen(
    onCardClick: (cardId: String) -> Unit,
    onShowSnackbar: suspend (message: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RandomCardsViewModel = hiltViewModel()
) {
    val randomCardsUiState by viewModel.randomCardsUiState.collectAsStateWithLifecycle()
    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = randomCardsUiState.refreshing,
            onRefresh = { viewModel.refreshRandomCards() }
        )

    Box(
        modifier = modifier.fillMaxSize().pullRefresh(state = pullRefreshState),
        contentAlignment = Alignment.Center
    ) {
        when (val uiState = randomCardsUiState) {
            is RandomCardsUiState.Success -> {
                CardsGallery(uiState.cards, onCardClick = onCardClick)
            }
            is RandomCardsUiState.Empty -> Unit
        }

        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = randomCardsUiState.refreshing,
            state = pullRefreshState
        )

        LaunchedEffect(randomCardsUiState.errorMessage) {
            if (randomCardsUiState.hasError()) {
                onShowSnackbar(randomCardsUiState.errorMessage())
            }
        }
    }
}

@Composable
private fun CardsGallery(cards: List<CardPreview>, onCardClick: (cardId: String) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        val scope = rememberCoroutineScope()
        val lazyGridState = rememberLazyGridState()

        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 2),
            state = lazyGridState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(all = 4.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cards, key = { it.id }) { card ->
                AsyncImage(
                    model =
                        ImageRequest.Builder(LocalContext.current)
                            .data(card.imageUrl)
                            .crossfade(true)
                            .build(),
                    contentDescription = card.name,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = DesignR.drawable.blank_card_placeholder),
                    modifier =
                        Modifier.fillMaxWidth()
                            .wrapContentHeight()
                            .rippleClickable(onClick = { onCardClick(card.id) })
                )
            }
        }

        val shouldShowScrollToTopButton by remember {
            derivedStateOf { lazyGridState.firstVisibleItemIndex > 0 }
        }
        ScrollToTopButton(
            visible = shouldShowScrollToTopButton,
            onClick = { scope.launch { lazyGridState.animateScrollToItem(0) } },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CardsGalleryPreview() {
    CardsGallery(cards = MockUtils.emptyCards, onCardClick = {})
}
