package com.donghanx.nowinmtg.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavKey
import com.donghanx.navigation.NavigationState
import com.donghanx.navigation.rememberNavigationState
import com.donghanx.nowinmtg.navigation.TOP_LEVEL_ROUTES
import com.donghanx.nowinmtg.navigation.TopLevelNavItem
import com.donghanx.randomcards.navigation.RandomCardsRoute

@Composable
fun rememberNowInMtgAppState(
    windowSizeClass: WindowSizeClass,
    navigationState: NavigationState =
        rememberNavigationState(startRoute = RandomCardsRoute, topLevelRoutes = TOP_LEVEL_ROUTES),
): NowInMtgAppState = remember {
    NowInMtgAppState(navigationState = navigationState, windowSizeClass = windowSizeClass)
}

@Stable
class NowInMtgAppState(
    val navigationState: NavigationState,
    private val windowSizeClass: WindowSizeClass,
) {
    val currentRoute: NavKey
        get() = navigationState.currentRoute

    val currentTopLevelRoute: NavKey
        get() = navigationState.topLevelRoute

    val currentTopLevelNavItem: TopLevelNavItem?
        get() = topLevelNavItems.find { navigationState.currentRouteIgnoringDialog == it.route }

    val isTopLevelRoute: Boolean
        get() = currentRoute in TOP_LEVEL_ROUTES

    val topLevelNavItems: List<TopLevelNavItem> = TopLevelNavItem.entries

    val shouldShowLeftNavigationRail: Boolean
        get() = windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
}
