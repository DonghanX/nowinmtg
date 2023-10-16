package com.donghanx.randomcards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.donghanx.design.R as DesignR
import com.donghanx.design.pullrefresh.PullRefreshIndicator
import com.donghanx.design.pullrefresh.pullRefresh
import com.donghanx.design.pullrefresh.rememberPullRefreshState
import com.donghanx.model.Card

@Composable
fun DefaultCardsScreen(modifier: Modifier = Modifier, viewModel: MainViewModel = viewModel()) {
    val refreshing by viewModel.refreshing.collectAsStateWithLifecycle()
    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = refreshing,
            onRefresh = { viewModel.refreshRandomCards() }
        )

    Box(
        modifier = modifier.fillMaxSize().pullRefresh(state = pullRefreshState),
        contentAlignment = Alignment.Center
    ) {
        val defaultCardsUiState by viewModel.randomCardsUiState.collectAsStateWithLifecycle()

        when (val uiState = defaultCardsUiState) {
            is RandomCardsUiState.Success -> {
                CardsGallery(uiState.cards)
            }
            RandomCardsUiState.Empty,
            RandomCardsUiState.Loading -> Unit
        }

        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = refreshing,
            state = pullRefreshState
        )

        val networkStatus by viewModel.networkStatus.collectAsStateWithLifecycle()
        NetworkErrorSnackbar(
            networkStatus = networkStatus,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun CardsGallery(cards: List<Card>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
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
                contentDescription = card.text,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = DesignR.drawable.blank_card_placeholder),
                modifier = Modifier.fillMaxWidth().wrapContentHeight()
            )
        }
    }
}

@Composable
private fun NetworkErrorSnackbar(networkStatus: NetworkStatus, modifier: Modifier = Modifier) {
    val (isNetworkErrorSnackbarVisible, setNetworkErorSnackbarVisible) =
        remember { mutableStateOf(false) }

    LaunchedEffect(networkStatus) { setNetworkErorSnackbarVisible(networkStatus.hasError) }

    AnimatedVisibility(visible = isNetworkErrorSnackbarVisible, modifier = modifier) {
        Snackbar(
            dismissAction = {
                Button(onClick = { setNetworkErorSnackbarVisible(false) }) {
                    Text(text = "Dismiss")
                }
            }
        ) {
            Text(text = networkStatus.errorMessage)
        }
    }
}
