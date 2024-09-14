package com.donghanx.favorites.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.donghanx.favorites.FavoritesScreen
import com.donghanx.model.CardPreview

const val FAVORITES_GRAPH_ROUTE = "FavoritesGraph"
const val FAVORITES_ROUTE = "Favorites"

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.favoriteGraph(
    onCardClick: (cacheKey: String, card: CardPreview, parentRoute: String) -> Unit,
    nestedGraphs: NavGraphBuilder.(parentRoute: String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
) {
    navigation(startDestination = FAVORITES_ROUTE, route = FAVORITES_GRAPH_ROUTE) {
        composable(route = FAVORITES_ROUTE) {
            FavoritesScreen(
                onCardClick = { cacheKey, card -> onCardClick(cacheKey, card, FAVORITES_ROUTE) },
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = this@composable,
            )
        }

        nestedGraphs(FAVORITES_ROUTE)
    }
}
