package com.donghanx.favorites.navigation

import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.donghanx.design.composable.provider.LocalNavAnimatedVisibilityScope
import com.donghanx.favorites.FavoritesScreen
import com.donghanx.model.CardPreview
import kotlinx.serialization.Serializable

@Serializable object FavoritesBaseRoute

@Serializable object FavoritesRoute

fun NavGraphBuilder.favoriteGraph(
    onCardClick: (card: CardPreview, parentRoute: String) -> Unit,
    nestedGraph: NavGraphBuilder.() -> Unit,
) {
    navigation<FavoritesBaseRoute>(startDestination = FavoritesRoute) {
        composable<FavoritesRoute> {
            CompositionLocalProvider(LocalNavAnimatedVisibilityScope provides this) {
                FavoritesScreen(
                    onCardClick = { card -> onCardClick(card, FavoritesRoute.toString()) }
                )
            }
        }
        nestedGraph()
    }
}
