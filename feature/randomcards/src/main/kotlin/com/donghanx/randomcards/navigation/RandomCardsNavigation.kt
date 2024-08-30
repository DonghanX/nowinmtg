package com.donghanx.randomcards.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.donghanx.randomcards.RandomCardsScreen

const val RANDOM_CARDS_GRAPH_ROUTE = "RandomCardsGraph"
const val RANDOM_CARDS_ROUTE = "RandomCards"

fun NavGraphBuilder.randomCardsGraph(
    onCardClick: (cardId: String, parentRoute: String) -> Unit,
    nestedGraphs: NavGraphBuilder.(parentRoute: String) -> Unit,
    onShowSnackbar: suspend (message: String) -> Unit,
) {
    navigation(startDestination = RANDOM_CARDS_ROUTE, route = RANDOM_CARDS_GRAPH_ROUTE) {
        composable(route = RANDOM_CARDS_ROUTE) {
            RandomCardsScreen(
                onCardClick = { cardId -> onCardClick(cardId, RANDOM_CARDS_ROUTE) },
                onShowSnackbar = onShowSnackbar,
            )
        }

        nestedGraphs(RANDOM_CARDS_ROUTE)
    }
}
