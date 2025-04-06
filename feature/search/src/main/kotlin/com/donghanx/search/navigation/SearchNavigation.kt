package com.donghanx.search.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.donghanx.model.SetInfo
import com.donghanx.search.SearchScreen
import kotlinx.serialization.Serializable

@Serializable object SearchRoute

fun NavController.navigateToSearch() {
    navigate(route = SearchRoute) { launchSingleTop = true }
}

fun NavGraphBuilder.searchScreen(onCloseClick: () -> Unit, onSetClick: (SetInfo) -> Unit) {
    composable<SearchRoute>(
        enterTransition = {
            slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Up)
        },
        exitTransition = {
            slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Down)
        },
    ) {
        SearchScreen(onCloseClick = onCloseClick, onSetClick = onSetClick)
    }
}
