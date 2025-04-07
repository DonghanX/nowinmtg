package com.donghanx.nowinmtg.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.donghanx.design.R as DesignR
import com.donghanx.design.ui.appbar.NowInMtgTopAppBar
import com.donghanx.design.ui.navigation.NowInMtgNavigationBarItem
import com.donghanx.design.ui.navigation.NowInMtgNavigationRailItem
import com.donghanx.nowinmtg.navigation.NimNavHost
import com.donghanx.nowinmtg.navigation.TopLevelDestination
import com.donghanx.search.navigation.navigateToSearch
import kotlin.reflect.KClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NowInMtgApp(windowSizeClass: WindowSizeClass) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        val appState = rememberNowInMtgAppState(windowSizeClass = windowSizeClass)
        val snackbarHostState = remember { SnackbarHostState() }
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                appState.currentTopLevelDestination?.let { topLevelDestination ->
                    NowInMtgTopAppBar(
                        titleResId = topLevelDestination.labelResId,
                        navigationIcon = Icons.Rounded.Search,
                        navigationIconContentDescription = stringResource(DesignR.string.search),
                        showNavigationIcon = topLevelDestination == TopLevelDestination.Sets,
                        shouldAdjustNavigationRail = appState.shouldShowLeftNavigationRail,
                        onNavigationIconClick = appState.navController::navigateToSearch,
                    )
                }
            },
            bottomBar = {
                if (appState.shouldShowBottomBar) {
                    BottomNavigationBar(
                        topLevelDestinations = appState.topLevelDestinations,
                        currentDestination = appState.currentDestination,
                        onNavItemClick = appState::navigateToTopLevelDestination,
                    )
                }
            },
        ) { paddingValues ->
            Row(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                if (appState.shouldShowLeftNavigationRail) {
                    LeftNavigationRail(
                        topLevelDestinations = appState.topLevelDestinations,
                        currentDestination = appState.currentDestination,
                        onNavItemClick = appState::navigateToTopLevelDestination,
                    )

                    Spacer(modifier = Modifier.width(width = 8.dp))
                }
                NimNavHost(
                    navController = appState.navController,
                    onShowSnackbar = { message ->
                        snackbarHostState.showSnackbar(message = message, withDismissAction = true)
                    },
                )
            }
        }
    }
}

@Composable
private fun BottomNavigationBar(
    topLevelDestinations: List<TopLevelDestination>,
    currentDestination: NavDestination?,
    onNavItemClick: (TopLevelDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier) {
        NavigationBar(containerColor = Color.Transparent) {
            topLevelDestinations.forEach { destination ->
                NowInMtgNavigationBarItem(
                    selected =
                        currentDestination.withinTopLevelDestinationInHierarchy(
                            destination.baseRoute
                        ),
                    onClick = { onNavItemClick(destination) },
                    selectedIcon = {
                        Icon(
                            painter = painterResource(id = destination.selectedIconResId),
                            contentDescription = stringResource(id = destination.labelResId),
                        )
                    },
                    unSelectedIcon = {
                        Icon(
                            painter = painterResource(id = destination.unselectedIconResId),
                            contentDescription = stringResource(id = destination.labelResId),
                        )
                    },
                    label = { Text(text = stringResource(id = destination.labelResId)) },
                )
            }
        }
    }
}

@Composable
private fun LeftNavigationRail(
    topLevelDestinations: List<TopLevelDestination>,
    currentDestination: NavDestination?,
    onNavItemClick: (TopLevelDestination) -> Unit,
) {
    NavigationRail(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
    ) {
        topLevelDestinations.forEach { destination ->
            NowInMtgNavigationRailItem(
                selected =
                    currentDestination.withinTopLevelDestinationInHierarchy(destination.baseRoute),
                onClick = { onNavItemClick(destination) },
                selectedIcon = {
                    Icon(
                        painter = painterResource(id = destination.selectedIconResId),
                        contentDescription = stringResource(id = destination.labelResId),
                    )
                },
                unSelectedIcon = {
                    Icon(
                        painter = painterResource(id = destination.unselectedIconResId),
                        contentDescription = stringResource(id = destination.labelResId),
                    )
                },
            ) {
                Text(text = stringResource(id = destination.labelResId))
            }
        }
    }
}

private fun NavDestination?.withinTopLevelDestinationInHierarchy(route: KClass<*>): Boolean =
    this?.hierarchy?.any { it.hasRoute(route) } ?: false
