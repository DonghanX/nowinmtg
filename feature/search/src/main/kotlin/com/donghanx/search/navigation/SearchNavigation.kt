package com.donghanx.search.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.donghanx.search.SearchScreen

const val SEARCH_ROUTE = "Search"

fun NavController.navigateToSearch() {
    navigate(route = SEARCH_ROUTE) { launchSingleTop = true }
}

fun NavGraphBuilder.searchScreen(
    onCloseClick: () -> Unit,
) {
    composable(
        route = SEARCH_ROUTE,
        enterTransition = {
            slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Up)
        },
        exitTransition = {
            slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Down)
        }
    ) {
        SearchScreen(onCloseClick = onCloseClick)
    }
}
