package com.donghanx.carddetails

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.donghanx.design.R as DesignR

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun CardDetailsScreen(
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (message: String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    modifier: Modifier = Modifier,
    viewModel: CardDetailsViewModel = hiltViewModel(),
) {
    val cardDetailsUiState by viewModel.cardDetailsUiState.collectAsStateWithLifecycle()

    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        isRefreshing = cardDetailsUiState.refreshing,
        onRefresh = viewModel::refreshCardDetails,
    ) {
        Column {
            val isCardFavorite by viewModel.isCardFavorite.collectAsStateWithLifecycle()
            CardDetailsTopBar(
                isCardFavorite = isCardFavorite,
                onBackClick = onBackClick,
                onFavoritesClick = { viewModel.onFavoriteClick() },
            )

            Box(
                modifier = Modifier.fillMaxSize().verticalScroll(state = rememberScrollState()),
                contentAlignment = Alignment.TopCenter,
            ) {
                when (val uiState = cardDetailsUiState) {
                    is CardDetailsUiState.Success -> {
                        CardDetailsView(
                            cardDetails = uiState.cardDetails,
                            rulings = uiState.rulings,
                        )
                    }
                    is CardDetailsUiState.NoCardDetails -> {
                        // TODO: use a more intuitive placeholder view instead
                        CircularProgressIndicator()
                    }
                }
            }
        }

        LaunchedEffect(cardDetailsUiState.errorMessage) {
            if (cardDetailsUiState.hasError()) {
                onShowSnackbar(cardDetailsUiState.errorMessage())
            }
        }
    }
}

@Composable
private fun CardDetailsTopBar(
    isCardFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth().padding(bottom = 12.dp),
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(id = DesignR.string.back),
            )
        }

        IconButton(onClick = onFavoritesClick) {
            Icon(
                imageVector =
                    if (isCardFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = stringResource(id = DesignR.string.favorites),
            )
        }
    }
}
