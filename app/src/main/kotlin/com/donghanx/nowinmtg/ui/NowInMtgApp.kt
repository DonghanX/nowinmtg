package com.donghanx.nowinmtg.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.donghanx.design.R as DesignR
import com.donghanx.design.ui.appbar.NowInMtgTopAppBar
import com.donghanx.nowinmtg.navigation.NimNavHost
import com.donghanx.nowinmtg.navigation.TopLevelDestination
import com.donghanx.nowinmtg.navigation.TopLevelDestinationScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NowInMtgApp() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        val appState = rememberNowInMtgAppState()
        val snackbarHostState = remember { SnackbarHostState() }
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopLevelDestinationScope(appState = appState) { topLevelDestination ->
                    NowInMtgTopAppBar(
                        titleResId = topLevelDestination.labelResId,
                        navigationIcon = Icons.Rounded.Search,
                        navigationIconContentDescription = stringResource(DesignR.string.search)
                    )
                }
            },
            bottomBar = {
                BottomNavigationBar(
                    topLevelDestinations = appState.topLevelDestinations,
                    onNavItemClick = { route -> appState.navigateToTopLevelDestination(route) }
                )
            }
        ) { paddingValues ->
            Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                NimNavHost(
                    navController = appState.navController,
                    onShowSnackbar = { message ->
                        snackbarHostState.showSnackbar(message = message, withDismissAction = true)
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomNavigationBar(
    topLevelDestinations: List<TopLevelDestination>,
    onNavItemClick: (route: String) -> Unit
) {
    NavigationBar(containerColor = Color.Transparent) {
        topLevelDestinations.forEach { destination ->
            NavigationBarItem(
                selected = false,
                onClick = { onNavItemClick(destination.route) },
                icon = {
                    Icon(
                        painter = painterResource(id = destination.iconResId),
                        contentDescription = stringResource(id = destination.labelResId)
                    )
                },
                label = { Text(text = stringResource(id = destination.labelResId)) }
            )
        }
    }
}
