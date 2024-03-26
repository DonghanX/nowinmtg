package com.donghanx.favorites.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.donghanx.favorites.FavoritesScreen

const val FAVORITES_ROUTE = "Favorites"

fun NavGraphBuilder.favoriteScreen() {
    composable(route = FAVORITES_ROUTE) { FavoritesScreen() }
}
