package com.donghanx.nowinmtg.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.donghanx.favorites.navigation.FavoritesBaseRoute
import com.donghanx.nowinmtg.navigation.TopLevelDestination
import com.donghanx.randomcards.navigation.RandomCardsBaseRoute
import com.donghanx.sets.navigation.SetsBaseRoute

@Composable
fun rememberNowInMtgAppState(
    navController: NavHostController = rememberNavController(),
    windowSizeClass: WindowSizeClass,
): NowInMtgAppState = remember {
    NowInMtgAppState(navController = navController, windowSizeClass = windowSizeClass)
}

@Stable
class NowInMtgAppState(
    val navController: NavHostController,
    private val windowSizeClass: WindowSizeClass,
) {

    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable
        get() {
            return topLevelDestinations.find { topLevelDestination ->
                currentDestination?.hasRoute(route = topLevelDestination.route) ?: false
            }
        }

    val topLevelDestinations: List<TopLevelDestination> =
        listOf(
            TopLevelDestination.RandomCards,
            TopLevelDestination.Sets,
            TopLevelDestination.Favorites,
        )

    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val shouldShowLeftNavigationRail: Boolean
        get() = !shouldShowBottomBar

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }

        val route: Any =
            when (topLevelDestination) {
                TopLevelDestination.RandomCards -> RandomCardsBaseRoute
                TopLevelDestination.Sets -> SetsBaseRoute
                TopLevelDestination.Favorites -> FavoritesBaseRoute
            }

        navController.navigate(route = route, navOptions = navOptions)
    }
}
