package com.donghanx.randomcards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.donghanx.design.pullrefresh.PullRefreshIndicator
import com.donghanx.design.pullrefresh.pullRefresh
import com.donghanx.design.pullrefresh.rememberPullRefreshState

@Composable
fun DefaultCardsScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    val refreshing by viewModel.refreshing.collectAsState()
    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = refreshing,
            onRefresh = {
                println("is refreshing? ${viewModel.refreshing.value}")
                viewModel.refreshRandomCards()
            }
        )

    Box(
        modifier = modifier.fillMaxSize().pullRefresh(state = pullRefreshState),
        contentAlignment = Alignment.Center
    ) {
        val defaultCardsUiState by viewModel.randomCardsUiState.collectAsState()

        when (val uiState = defaultCardsUiState) {
            is RandomCardsUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(uiState.cards, key = { it.id }) { card ->
                        Text(text = card.name, textAlign = TextAlign.Center)
                    }
                }
            }
            RandomCardsUiState.Empty -> {
                Text(text = "Cards are unavailable at this time")
            }
            RandomCardsUiState.Loading -> {
                CircularProgressIndicator()
            }
        }

        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = refreshing,
            state = pullRefreshState
        )

        val (isNetworkErrorSnackbarVisible, setNetworkErorSnackbarVisible) =
            remember { mutableStateOf(false) }
        val networkStatus by viewModel.networkStatus.collectAsState()

        LaunchedEffect(networkStatus) { setNetworkErorSnackbarVisible(networkStatus.hasError) }

        AnimatedVisibility(visible = isNetworkErrorSnackbarVisible) {
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
}
