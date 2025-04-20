package com.donghanx.nowinmtg.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    private var previousDestination by mutableStateOf<NavDestination?>(null)

    val currentDestination: NavDestination?
        @Composable
        get() {
            val targetDestination = navController.currentBackStackEntryAsState().value?.destination
            // Fallback to previousDestination if currentEntry is null. This is to prevent a UI
            // issue that happens during a transition between two destinations. In that case, the
            // current navigation back stack entry might be null, making the TopBar briefly
            // disappears and then shows up again and thus cause flickers.
            return targetDestination?.also { previousDestination = it } ?: previousDestination
        }

    val currentTopLevelDestination: TopLevelDestination?
        @Composable
        get() =
            topLevelDestinations.find { topLevelDestination ->
                currentDestination?.hasRoute(route = topLevelDestination.route) == true
            }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    val shouldShowLeftNavigationRail: Boolean
        get() = windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact

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
