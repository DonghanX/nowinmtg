package com.donghanx.carddetails.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
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
import com.donghanx.common.INVALID_ID

const val CARD_DETAILS_ROUTE = "CardDetails"
internal const val IMAGE_CACHE_KEY_ARGS = "ImageCacheKey"
internal const val CARD_ID_ARGS = "CardId"
internal const val MULTIVERSE_ID_ARGS = "MultiverseId"

fun NavController.navigateToCardDetails(
    imageCacheKey: String,
    cardId: String,
    parentRoute: String,
) {
    navigate(route = "${cardDetailsRoute(parentRoute)}/$imageCacheKey?$CARD_ID_ARGS=$cardId") {
        launchSingleTop = true
    }
}

fun NavController.navigateToCardDetailsWithMultiverseId(
    imageCacheKey: String,
    multiverseId: Int,
    parentRoute: String,
) {
    navigate(
        route = "${cardDetailsRoute(parentRoute)}/$imageCacheKey?$MULTIVERSE_ID_ARGS=$multiverseId"
    ) {
        launchSingleTop = true
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.cardDetailsScreen(
    parentRoute: String,
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (message: String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
) {
    composable(
        route =
            "${cardDetailsRoute(parentRoute)}/{$IMAGE_CACHE_KEY_ARGS}?${optionalArgs(CARD_ID_ARGS)}&${optionalArgs(MULTIVERSE_ID_ARGS)}",
        arguments =
            listOf(
                navArgument(IMAGE_CACHE_KEY_ARGS) { type = NavType.StringType },
                navArgument(CARD_ID_ARGS) {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(MULTIVERSE_ID_ARGS) {
                    type = NavType.IntType
                    defaultValue = INVALID_ID
                },
            ),
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
    ) {
        CardDetailsScreen(
            cacheKey = it.arguments?.getString(IMAGE_CACHE_KEY_ARGS).orEmpty(),
            onBackClick = onBackClick,
            onShowSnackbar = onShowSnackbar,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this@composable,
        )
    }
}

private fun cardDetailsRoute(parentRoute: String): String = "${CARD_DETAILS_ROUTE}_$parentRoute"

private fun optionalArgs(argsName: String): String = "$argsName={$argsName}"
