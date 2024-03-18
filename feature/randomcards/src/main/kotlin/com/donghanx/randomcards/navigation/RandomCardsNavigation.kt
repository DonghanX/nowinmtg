package com.donghanx.randomcards.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.donghanx.randomcards.RandomCardsScreen

const val RANDOM_CARDS_ROUTE = "RandomCards"

fun NavGraphBuilder.randomCardsScreen(
    onCardClick: (cardId: String) -> Unit,
    onShowSnackbar: suspend (message: String) -> Unit,
) {
    composable(
        route = RANDOM_CARDS_ROUTE,
        enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300, easing = LinearEasing)) +
                slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start)
        },
        exitTransition = {
            fadeOut(animationSpec = tween(durationMillis = 300, easing = LinearEasing)) +
                slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.End)
        }
    ) {
        RandomCardsScreen(onCardClick = onCardClick, onShowSnackbar = onShowSnackbar)
    }
}
