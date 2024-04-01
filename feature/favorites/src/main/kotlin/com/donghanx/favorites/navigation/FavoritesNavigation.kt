package com.donghanx.favorites.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.donghanx.favorites.FavoritesScreen

const val FAVORITES_GRAPH_ROUTE = "FavoritesGraph"
const val FAVORITES_ROUTE = "Favorites"

fun NavGraphBuilder.favoriteGraph(
    onCardClick: (cardId: String, parentRoute: String) -> Unit,
    nestedGraphs: NavGraphBuilder.(parentRoute: String) -> Unit
) {
    navigation(startDestination = FAVORITES_ROUTE, route = FAVORITES_GRAPH_ROUTE) {
        composable(
            route = FAVORITES_ROUTE,
        ) {
            FavoritesScreen(onCardClick = { cardId -> onCardClick(cardId, FAVORITES_ROUTE) })
        }

        nestedGraphs(FAVORITES_ROUTE)
    }
}
