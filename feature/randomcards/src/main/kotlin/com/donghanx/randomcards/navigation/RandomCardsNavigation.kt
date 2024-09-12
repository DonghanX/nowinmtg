package com.donghanx.randomcards.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.donghanx.randomcards.RandomCardsScreen

const val RANDOM_CARDS_GRAPH_ROUTE = "RandomCardsGraph"
const val RANDOM_CARDS_ROUTE = "RandomCards"

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.randomCardsGraph(
    onCardClick: (index: Int, multiverseId: Int, parentRoute: String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    nestedGraphs: NavGraphBuilder.(parentRoute: String) -> Unit,
    onShowSnackbar: suspend (message: String) -> Unit,
) {
    navigation(startDestination = RANDOM_CARDS_ROUTE, route = RANDOM_CARDS_GRAPH_ROUTE) {
        composable(route = RANDOM_CARDS_ROUTE) {
            RandomCardsScreen(
                onCardClick = { index, multiverseId -> onCardClick(index, multiverseId, RANDOM_CARDS_ROUTE) },
                onShowSnackbar = onShowSnackbar,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = this@composable,
            )
        }

        nestedGraphs(RANDOM_CARDS_ROUTE)
    }
}
