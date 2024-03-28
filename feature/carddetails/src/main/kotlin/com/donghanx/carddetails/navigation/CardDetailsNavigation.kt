package com.donghanx.carddetails.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.donghanx.carddetails.CardDetailsScreen

const val CARD_DETAILS_ROUTE = "CardDetails"
internal const val CARD_ID_ARGS = "CardId"

fun NavController.navigateToCardDetails(cardId: String) {
    navigate(route = "$CARD_DETAILS_ROUTE/$cardId") { launchSingleTop = true }
}

fun NavGraphBuilder.cardDetailsScreen(
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (message: String) -> Unit
) {
    composable(
        route = "$CARD_DETAILS_ROUTE/{$CARD_ID_ARGS}",
        enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300, easing = LinearEasing)) +
                slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start)
        },
        exitTransition = {
            fadeOut(animationSpec = tween(durationMillis = 300, easing = LinearEasing)) +
                slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.End)
        }
    ) {
        CardDetailsScreen(onBackClick = onBackClick, onShowSnackbar = onShowSnackbar)
    }
}
