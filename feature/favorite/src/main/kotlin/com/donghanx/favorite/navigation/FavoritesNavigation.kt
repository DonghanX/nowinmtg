package com.donghanx.favorite.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.donghanx.favorite.FavoriteScreen

const val FAVORITE_ROUTE = "Favorite"

fun NavGraphBuilder.favoriteScreen() {
    composable(route = FAVORITE_ROUTE) { FavoriteScreen() }
}
