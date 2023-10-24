package com.donghanx.carddetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.donghanx.design.ui.pullrefresh.PullRefreshIndicator
import com.donghanx.design.ui.pullrefresh.pullRefresh
import com.donghanx.design.ui.pullrefresh.rememberPullRefreshState

@Composable
fun CardDetailsScreen(
    onShowSnackbar: suspend (message: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CardDetailsViewModel = hiltViewModel()
) {
    val cardDetailsUiState by viewModel.cardDetailsUiState.collectAsStateWithLifecycle()

    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = cardDetailsUiState.refreshing,
            onRefresh = { viewModel.refreshCardDetails() }
        )

    Box(
        modifier = modifier.fillMaxSize().pullRefresh(state = pullRefreshState),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val uiState = cardDetailsUiState) {
                is CardDetailsUiState.Success -> {
                    CardDetailsView(cardDetails = uiState.cardDetails)
                }
                is CardDetailsUiState.NoCardDetails -> {
                    // TODO: use a more intuitive placeholder view instead
                    CircularProgressIndicator()
                }
            }
        }

        PullRefreshIndicator(
            refreshing = cardDetailsUiState.refreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        LaunchedEffect(cardDetailsUiState.errorMessage) {
            if (cardDetailsUiState.hasError()) {
                onShowSnackbar(cardDetailsUiState.errorMessage())
            }
        }
    }
}
