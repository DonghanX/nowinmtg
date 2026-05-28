package com.donghanx.carddetails.navigation

import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.metadata
import com.donghanx.carddetails.CardDetailsScreen
import com.donghanx.carddetails.CardDetailsViewModel
import com.donghanx.design.animation.enterTransitionHorizontal
import com.donghanx.design.animation.exitTransitionHorizontal
import com.donghanx.design.animation.fadeInTween
import com.donghanx.design.animation.fadeOutTween
import com.donghanx.design.composable.provider.NavAnimatedVisibilityProviderWrapper
import com.donghanx.navigation.NavKeyEntryProviderScope
import com.donghanx.navigation.Navigator
import com.donghanx.navigation.extensions.popTransition
import com.donghanx.navigation.extensions.transition
import kotlinx.serialization.Serializable

@Serializable
data class CardDetailsRoute(
    val cardId: String?,
    val multiverseId: Int?,
    val previewImageUrl: String?,
    val parentRoute: String,
) : NavKey {
    val cachedKeyId: String?
        get() = cardId ?: multiverseId?.toString()
}

fun Navigator.navigateToCardDetails(cardId: String, previewImageUrl: String?, parentRoute: String) {
    val cardDetailsRoute =
        CardDetailsRoute(
            cardId = cardId,
            multiverseId = null,
            previewImageUrl = previewImageUrl,
            parentRoute = parentRoute,
        )
    navigate(cardDetailsRoute)
}

fun Navigator.navigateToCardDetailsWithMultiverseId(
    previewImageUrl: String?,
    multiverseId: Int,
    parentRoute: String,
) {
    val cardDetailsRoute =
        CardDetailsRoute(
            multiverseId = multiverseId,
            cardId = null,
            previewImageUrl = previewImageUrl,
            parentRoute = parentRoute,
        )
    navigate(cardDetailsRoute)
}

fun NavKeyEntryProviderScope.cardDetailsEntry(
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (message: String) -> Unit,
) {
    entry<CardDetailsRoute>(
        metadata =
            metadata {
                transition { enterTransitionHorizontal() togetherWith fadeOutTween() }
                popTransition { fadeInTween() togetherWith exitTransitionHorizontal() }
            }
    ) { cardDetailsRoute ->
        NavAnimatedVisibilityProviderWrapper {
            CardDetailsScreen(
                cardDetailsRoute = cardDetailsRoute,
                onBackClick = onBackClick,
                onShowSnackbar = onShowSnackbar,
                viewModel = cardDetailsViewModel(cardDetailsRoute),
            )
        }
    }
}

@Composable
private fun cardDetailsViewModel(route: CardDetailsRoute): CardDetailsViewModel =
    hiltViewModel<CardDetailsViewModel, CardDetailsViewModel.Factory>(
        creationCallback = { factory -> factory.create(route) }
    )
