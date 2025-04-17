package com.donghanx.setdetails.navigation

import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.donghanx.design.composable.provider.LocalNavAnimatedVisibilityScope
import com.donghanx.model.CardPreview
import com.donghanx.setdetails.SetDetailsScreen
import kotlinx.serialization.Serializable

@Serializable data class SetDetailsRoute(val setId: String, val parentRoute: String)

fun NavController.navigateToSetDetails(setId: String, parentRoute: String) {
    navigate(SetDetailsRoute(setId = setId, parentRoute = parentRoute))
}

fun NavGraphBuilder.setDetailsScreen(
    onBackClick: () -> Unit,
    onCardClick: (CardPreview) -> Unit,
    onShowSnackbar: suspend (String) -> Unit,
) {
    composable<SetDetailsRoute> {
        CompositionLocalProvider(LocalNavAnimatedVisibilityScope provides this) {
            SetDetailsScreen(
                onBackClick = onBackClick,
                onCardClick = onCardClick,
                onShowSnackbar = onShowSnackbar,
            )
        }
    }
}
