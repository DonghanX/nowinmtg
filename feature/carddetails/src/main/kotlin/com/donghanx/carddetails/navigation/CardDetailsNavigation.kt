package com.donghanx.carddetails.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.donghanx.carddetails.CardDetailsScreen
import com.donghanx.design.composable.provider.LocalNavAnimatedVisibilityScope
import kotlinx.serialization.Serializable

@Serializable
data class CardDetailsRoute(
    val cardId: String?,
    val multiverseId: Int?,
    val previewImageUrl: String?,
    val parentRoute: String,
)

fun NavController.navigateToCardDetails(
    cardId: String,
    previewImageUrl: String?,
    parentRoute: String,
) {
    val cardDetailsRoute =
        CardDetailsRoute(
            cardId = cardId,
            multiverseId = null,
            previewImageUrl = previewImageUrl,
            parentRoute = parentRoute,
        )
    navigate(cardDetailsRoute) { launchSingleTop = true }
}

fun NavController.navigateToCardDetailsWithMultiverseId(
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
    navigate(cardDetailsRoute) { launchSingleTop = true }
}

fun NavGraphBuilder.cardDetailsScreen(
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (message: String) -> Unit,
) {
    composable<CardDetailsRoute>(
        enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300, easing = LinearEasing)) +
                slideIntoContainer(
                    animationSpec = tween(durationMillis = 300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                )
        },
        exitTransition = {
            fadeOut(animationSpec = tween(durationMillis = 300, easing = LinearEasing)) +
                slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                )
        },
    ) { backStackEntry ->
        val cardDetailsRoute = backStackEntry.toRoute<CardDetailsRoute>()

        CompositionLocalProvider(LocalNavAnimatedVisibilityScope provides this) {
            CardDetailsScreen(
                cacheKeyId = cardDetailsRoute.cardId ?: cardDetailsRoute.multiverseId?.toString(),
                previewImageUrl = cardDetailsRoute.previewImageUrl.orEmpty(),
                parentRoute = cardDetailsRoute.parentRoute,
                onBackClick = onBackClick,
                onShowSnackbar = onShowSnackbar,
            )
        }
    }
}
