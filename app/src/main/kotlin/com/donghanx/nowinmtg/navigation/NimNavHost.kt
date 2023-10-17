package com.donghanx.nowinmtg.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.donghanx.randomcards.navigation.RANDOM_CARDS_ROUTE
import com.donghanx.randomcards.navigation.randomCardsScreen

@Composable
fun NimNavHost(
    navController: NavHostController,
    onShowSnackbar: suspend (String) -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = RANDOM_CARDS_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        randomCardsScreen(onShowSnackbar = onShowSnackbar)
    }
}