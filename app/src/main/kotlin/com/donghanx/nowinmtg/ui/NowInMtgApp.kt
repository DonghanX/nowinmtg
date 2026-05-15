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
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldState
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.adaptive.navigationsuite.rememberNavigationSuiteScaffoldState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.donghanx.design.R as DesignR
import com.donghanx.design.composable.extensions.animateReset
import com.donghanx.design.composable.extensions.conditional
import com.donghanx.design.composable.provider.NotInMtgProvidersWrapper
import com.donghanx.design.ui.appbar.NowInMtgTopAppBar
import com.donghanx.navigation.Navigator
import com.donghanx.nowinmtg.navigation.NowInMtgNavDisplay
import com.donghanx.nowinmtg.navigation.TopLevelNavItem
import com.donghanx.nowinmtg.navigation.rememberTopAppBarStatesByTopLevelDestination
import com.donghanx.search.navigation.navigateToSearch
import com.donghanx.settings.navigation.navigateToSettings
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
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
        val navigationSuiteState = rememberNavigationSuiteScaffoldState()

        val navigator = remember { Navigator(appState.navigationState) }

        NavigationSuiteScaffold(
            navigationSuiteItems = {
                TopLevelNavItem.entries.forEachIndexed { index, topLevelNavItem ->
                    val route = topLevelNavItem.route
                    val selected = route == appState.currentTopLevelRoute

                    navigationSuiteItem(
                        selected = selected,
                        topLevelNavItem = topLevelNavItem,
                        isFirstItem = index == 0,
                        isNavigationRailItem = appState.shouldShowLeftNavigationRail,
                        onClick = { navigator.navigate(route) },
                    )
                }
            },
            state = navigationSuiteState,
            layoutType = adaptiveInfo.toNavigationSuiteType(appState.shouldShowLeftNavigationRail),
        ) {
            val topAppBarStates = rememberTopAppBarStatesByTopLevelDestination()
            val currentAppBarState =
                topAppBarStates[appState.currentTopLevelNavItem] ?: rememberTopAppBarState()
            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(currentAppBarState)
            var isBottomNavBarAnimating by remember { mutableStateOf(false) }

            if (!appState.shouldShowLeftNavigationRail && appState.isTopLevelRoute) {
                BottomNavigationBarScrollSyncEffect(
                    appBarState = scrollBehavior.state,
                    navigationSuiteState = navigationSuiteState,
                    onNavBarAnimatingChanged = { isBottomNavBarAnimating = it },
                )
            }

            Scaffold(
                // Only participate in nested scroll if the current screen is a top-level
                // destination which has TopAppBar.
                // This prevents scroll conflicts with sub-screens (e.g., SetDetails screen) that
                // manage their own header and nested scrolling logic.
                modifier =
                    Modifier.conditional(appState.isTopLevelRoute) {
                        nestedScroll(scrollBehavior.nestedScrollConnection)
                    },
                containerColor = Color.Transparent,
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                snackbarHost = { SnackbarHost(snackbarHostState) },
                topBar = {
                    appState.currentTopLevelNavItem?.let { topLevelDestination ->
                        NowInMtgTopAppBar(
                            title = stringResource(topLevelDestination.labelResId),
                            navigationIcon = Icons.Rounded.Search,
                            navigationIconContentDescription =
                                stringResource(DesignR.string.search),
                            actionIcon = Icons.Rounded.Settings,
                            actionIconContentDescription = stringResource(DesignR.string.settings),
                            showNavigationIcon = topLevelDestination == TopLevelNavItem.Sets,
                            shouldAdjustNavigationRail = appState.shouldShowLeftNavigationRail,
                            scrollBehavior = scrollBehavior,
                            onNavigationIconClick = navigator::navigateToSearch,
                            onActionIconClick = navigator::navigateToSettings,
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
                    NotInMtgProvidersWrapper(isBottomNavBarAnimating = isBottomNavBarAnimating) {
                        NowInMtgNavDisplay(
                            navigator = navigator,
                            navigationState = appState.navigationState,
                            onScrollToTop = {
                                coroutineScope.launch {
                                    currentAppBarState.animateReset(
                                        scrollBehavior.snapAnimationSpec
                                    )
                                }
                            },
                            onTopBarVisibilityChanged = { isTopBarCollapsed ->
                                coroutineScope.launch {
                                    if (!appState.shouldShowLeftNavigationRail) {
                                        navigationSuiteState.hideOrShow(isTopBarCollapsed)
                                    }
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
}

/**
 * A LaunchedEffect that syncs [navigationSuiteState] visibility with the TopAppBar's [appBarState]
 * to provide an immersive scrolling experience in screens with a large set of contents.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomNavigationBarScrollSyncEffect(
    appBarState: TopAppBarState,
    navigationSuiteState: NavigationSuiteScaffoldState,
    onNavBarAnimatingChanged: (isAnimating: Boolean) -> Unit,
) {
    LaunchedEffect(appBarState) {
        snapshotFlow { appBarState.collapsedFraction }
            .map { collapsedFraction -> collapsedFraction > 0.5F }
            .distinctUntilChanged()
            .onEach { isTopBarCollapsed ->
                onNavBarAnimatingChanged(true)
                navigationSuiteState.hideOrShow(isTopBarCollapsed)
                onNavBarAnimatingChanged(false)
            }
            .collect()
    }
}

private fun NavigationSuiteScope.navigationSuiteItem(
    selected: Boolean,
    topLevelNavItem: TopLevelNavItem,
    isFirstItem: Boolean,
    isNavigationRailItem: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val iconResId =
        if (selected) topLevelNavItem.selectedIconResId else topLevelNavItem.unselectedIconResId

    item(
        selected = selected,
        icon = {
            Icon(
                painter = painterResource(iconResId),
                contentDescription = stringResource(topLevelNavItem.labelResId),
            )
        },
        label = { Text(text = stringResource(topLevelNavItem.labelResId)) },
        onClick = onClick,
        modifier =
            if (isNavigationRailItem) modifier.padding(top = if (isFirstItem) 64.dp else 0.dp)
            else modifier,
    )
}

private fun WindowAdaptiveInfo.toNavigationSuiteType(shouldShowNavigationRail: Boolean) =
    if (shouldShowNavigationRail) NavigationSuiteType.NavigationRail
    else NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(this)

private suspend fun NavigationSuiteScaffoldState.hideOrShow(shouldCollapse: Boolean) =
    if (shouldCollapse) hide() else show()
