package com.donghanx.nowinmtg.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.donghanx.nowinmtg.navigation.TopLevelDestination
import com.donghanx.randomcards.navigation.RANDOM_CARDS_ROUTE
import com.donghanx.sets.navigation.SETS_ROUTE

@Composable
fun rememberNowInMtgAppState(
    navController: NavHostController = rememberNavController()
): NowInMtgAppState {
    return remember { NowInMtgAppState(navController = navController) }
}

@Stable
class NowInMtgAppState(val navController: NavHostController) {

    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable
        get() =
            when (currentDestination?.route) {
                RANDOM_CARDS_ROUTE -> TopLevelDestination.RandomCards
                SETS_ROUTE -> TopLevelDestination.Sets
                else -> null
            }

    val topLevelDestinations: List<TopLevelDestination> =
        listOf(TopLevelDestination.RandomCards, TopLevelDestination.Sets, TopLevelDestination.Favorite)

    fun navigateToTopLevelDestination(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }
}
