package com.donghanx.favorites.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.donghanx.favorites.FavoritesScreen

const val FAVORITES_GRAPH_ROUTE = "FavoritesGraph"
const val FAVORITES_ROUTE = "Favorites"

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.favoriteGraph(
    onCardClick: (index: Int, cardId: String, parentRoute: String) -> Unit,
    nestedGraphs: NavGraphBuilder.(parentRoute: String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
) {
    navigation(startDestination = FAVORITES_ROUTE, route = FAVORITES_GRAPH_ROUTE) {
        composable(route = FAVORITES_ROUTE) {
            FavoritesScreen(
                onCardClick = { index, cardId -> onCardClick(index, cardId, FAVORITES_ROUTE) },
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = this@composable,
            )
        }

        nestedGraphs(FAVORITES_ROUTE)
    }
}
