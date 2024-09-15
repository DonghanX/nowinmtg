package com.donghanx.favorites.navigation

import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.donghanx.design.composable.provider.LocalNavAnimatedVisibilityScope
import com.donghanx.favorites.FavoritesScreen
import com.donghanx.model.CardPreview

const val FAVORITES_GRAPH_ROUTE = "FavoritesGraph"
const val FAVORITES_ROUTE = "Favorites"

fun NavGraphBuilder.favoriteGraph(
    onCardClick: (cacheKey: String, card: CardPreview, parentRoute: String) -> Unit,
    nestedGraphs: NavGraphBuilder.(parentRoute: String) -> Unit,
) {
    navigation(startDestination = FAVORITES_ROUTE, route = FAVORITES_GRAPH_ROUTE) {
        composable(route = FAVORITES_ROUTE) {
            CompositionLocalProvider(LocalNavAnimatedVisibilityScope provides this) {
                FavoritesScreen(
                    onCardClick = { cacheKey, card -> onCardClick(cacheKey, card, FAVORITES_ROUTE) }
                )
            }
        }

        nestedGraphs(FAVORITES_ROUTE)
    }
}
