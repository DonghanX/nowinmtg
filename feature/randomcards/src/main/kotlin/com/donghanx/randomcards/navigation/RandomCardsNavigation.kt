package com.donghanx.randomcards.navigation

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
    ) {
        RandomCardsScreen(onCardClick = onCardClick, onShowSnackbar = onShowSnackbar)
    }
}
