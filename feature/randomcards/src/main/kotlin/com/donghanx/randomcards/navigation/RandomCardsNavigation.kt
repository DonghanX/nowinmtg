package com.donghanx.randomcards.navigation

import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.donghanx.design.composable.provider.LocalNavAnimatedVisibilityScope
import com.donghanx.model.CardPreview
import com.donghanx.randomcards.RandomCardsScreen

const val RANDOM_CARDS_GRAPH_ROUTE = "RandomCardsGraph"
const val RANDOM_CARDS_ROUTE = "RandomCards"

fun NavGraphBuilder.randomCardsGraph(
    onCardClick: (cacheKey: String, card: CardPreview, parentRoute: String) -> Unit,
    nestedGraphs: NavGraphBuilder.(parentRoute: String) -> Unit,
    onShowSnackbar: suspend (message: String) -> Unit,
) {
    navigation(startDestination = RANDOM_CARDS_ROUTE, route = RANDOM_CARDS_GRAPH_ROUTE) {
        composable(route = RANDOM_CARDS_ROUTE) {
            CompositionLocalProvider(LocalNavAnimatedVisibilityScope provides this) {
                RandomCardsScreen(
                    onCardClick = { cacheKey, card ->
                        onCardClick(cacheKey, card, RANDOM_CARDS_ROUTE)
                    },
                    onShowSnackbar = onShowSnackbar,
                )
            }
        }

        nestedGraphs(RANDOM_CARDS_ROUTE)
    }
}
