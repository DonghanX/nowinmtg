package com.donghanx.nowinmtg.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.donghanx.design.R as DesignR
import com.donghanx.design.composable.extensions.animateReset
import com.donghanx.design.composable.extensions.conditional
import com.donghanx.design.ui.appbar.NowInMtgTopAppBar
import com.donghanx.nowinmtg.navigation.NimNavHost
import com.donghanx.nowinmtg.navigation.TopLevelDestination
import com.donghanx.nowinmtg.navigation.rememberTopAppBarStatesByTopLevelDestination
import com.donghanx.search.navigation.navigateToSearch
import com.donghanx.settings.navigation.navigateToSettings
import kotlin.reflect.KClass
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NowInMtgApp(
    windowSizeClass: WindowSizeClass,
    adaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        val appState = rememberNowInMtgAppState(windowSizeClass = windowSizeClass)
        val snackbarHostState = remember { SnackbarHostState() }
        val currentDestination = appState.currentDestination

        NavigationSuiteScaffold(
            navigationSuiteItems = {
                appState.topLevelDestinations.forEachIndexed { index, topLevelDestination ->
                    val selected =
                        currentDestination.withinTopLevelDestinationInHierarchy(
                            topLevelDestination.baseRoute
                        )

                    navigationSuiteItem(
                        selected = selected,
                        topLevelDestination = topLevelDestination,
                        isFirstItem = index == 0,
                        isNavigationRailItem = appState.shouldShowLeftNavigationRail,
                        onClick = { appState.navigateToTopLevelDestination(topLevelDestination) },
                    )
                }
            },
            layoutType = adaptiveInfo.toNavigationSuiteType(appState.shouldShowLeftNavigationRail),
            navigationSuiteColors =
                NavigationSuiteDefaults.colors(
                    navigationBarContainerColor = MaterialTheme.colorScheme.inverseOnSurface
                ),
        ) {
            val topAppBarStates = rememberTopAppBarStatesByTopLevelDestination()
            val currentAppBarState =
                topAppBarStates[appState.currentTopLevelDestination] ?: rememberTopAppBarState()
            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(currentAppBarState)
            Scaffold(
                // Only participate in nested scroll if the current screen is a top-level
                // destination which has TopAppBar.
                // This prevents scroll conflicts with sub-screens (e.g., SetDetails screen) that
                // manage their own header and nested scrolling logic.
                modifier =
                    Modifier.conditional(appState.isTopLevelDestination) {
                        nestedScroll(scrollBehavior.nestedScrollConnection)
                    },
                containerColor = Color.Transparent,
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                snackbarHost = { SnackbarHost(snackbarHostState) },
                topBar = {
                    appState.currentTopLevelDestination?.let { topLevelDestination ->
                        NowInMtgTopAppBar(
                            title = stringResource(topLevelDestination.labelResId),
                            navigationIcon = Icons.Rounded.Search,
                            navigationIconContentDescription =
                                stringResource(DesignR.string.search),
                            actionIcon = Icons.Rounded.Settings,
                            actionIconContentDescription = stringResource(DesignR.string.settings),
                            showNavigationIcon = topLevelDestination == TopLevelDestination.Sets,
                            shouldAdjustNavigationRail = appState.shouldShowLeftNavigationRail,
                            scrollBehavior = scrollBehavior,
                            onNavigationIconClick = appState.navController::navigateToSearch,
                            onActionIconClick = appState.navController::navigateToSettings,
                        )
                    }
                },
            ) { paddingValues ->
                Row(
                    modifier =
                        Modifier.fillMaxSize()
                            .padding(paddingValues)
                            .consumeWindowInsets(paddingValues)
                ) {
                    val coroutineScope = rememberCoroutineScope()
                    NimNavHost(
                        navController = appState.navController,
                        onScrollToTop = {
                            coroutineScope.launch {
                                currentAppBarState.animateReset(
                                    animationSpec = scrollBehavior.snapAnimationSpec
                                )
                            }
                        },
                        onShowSnackbar = { message ->
                            snackbarHostState.showSnackbar(
                                message = message,
                                withDismissAction = true,
                            )
                        },
                    )
                }
            }
        }
    }
}

private fun NavigationSuiteScope.navigationSuiteItem(
    selected: Boolean,
    topLevelDestination: TopLevelDestination,
    isFirstItem: Boolean,
    isNavigationRailItem: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val iconResId =
        if (selected) topLevelDestination.selectedIconResId
        else topLevelDestination.unselectedIconResId

    item(
        selected = selected,
        icon = {
            Icon(
                painter = painterResource(iconResId),
                contentDescription = stringResource(topLevelDestination.labelResId),
            )
        },
        label = { Text(text = stringResource(topLevelDestination.labelResId)) },
        onClick = onClick,
        modifier =
            if (isNavigationRailItem) modifier.padding(top = if (isFirstItem) 64.dp else 0.dp)
            else modifier,
    )
}

private fun WindowAdaptiveInfo.toNavigationSuiteType(shouldShowNavigationRail: Boolean) =
    if (shouldShowNavigationRail) NavigationSuiteType.NavigationRail
    else NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(this)

private fun NavDestination?.withinTopLevelDestinationInHierarchy(route: KClass<*>): Boolean =
    this?.hierarchy?.any { it.hasRoute(route) } ?: false
