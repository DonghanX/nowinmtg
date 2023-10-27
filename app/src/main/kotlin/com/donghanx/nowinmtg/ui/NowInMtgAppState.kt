package com.donghanx.nowinmtg.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.donghanx.nowinmtg.navigation.TopLevelDestination

@Composable
fun rememberNowInMtgAppState(
    navController: NavHostController = rememberNavController()
): NowInMtgAppState {
    return remember { NowInMtgAppState(navController = navController) }
}

@Stable
class NowInMtgAppState(val navController: NavHostController) {

    val topLevelDestinations: List<TopLevelDestination> = listOf(TopLevelDestination.RandomCards)

    fun navigateToTopLevelDestination(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }
}
