package com.donghanx.carddetails.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.donghanx.carddetails.CardDetailsScreen

const val CARD_DETAILS_ROUTE = "CardDetails"
internal const val CARD_ID_ARGS = "CardId"

fun NavController.navigateToCardDetails(cardId: Int, parentRoute: String) {
    navigate(route = "${cardDetailsRoute(parentRoute)}/$cardId") { launchSingleTop = true }
}

fun NavGraphBuilder.cardDetailsScreen(
    parentRoute: String,
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (message: String) -> Unit,
) {
    composable(
        route = "${cardDetailsRoute(parentRoute)}/{$CARD_ID_ARGS}",
        arguments = listOf(navArgument(CARD_ID_ARGS) { type = NavType.IntType }),
        enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300, easing = LinearEasing)) +
                slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start)
        },
        exitTransition = {
            fadeOut(animationSpec = tween(durationMillis = 300, easing = LinearEasing)) +
                slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.End)
        },
    ) {
        CardDetailsScreen(onBackClick = onBackClick, onShowSnackbar = onShowSnackbar)
    }
}

private fun cardDetailsRoute(parentRoute: String): String = "${CARD_DETAILS_ROUTE}_$parentRoute"
