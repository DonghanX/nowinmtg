package com.donghanx.sets.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.donghanx.model.SetInfo
import com.donghanx.sets.SetsScreen
import kotlinx.serialization.Serializable

@Serializable object SetsBaseRoute

@Serializable object SetsRoute

fun NavGraphBuilder.setsScreen(
    onSetClick: (SetInfo) -> Unit,
    onShowSnackbar: suspend (message: String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation<SetsBaseRoute>(startDestination = SetsRoute) {
        composable<SetsRoute> {
            SetsScreen(onShowSnackbar = onShowSnackbar, onSetClick = onSetClick)
        }

        nestedGraphs()
    }
}
