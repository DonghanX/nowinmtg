package com.donghanx.setdetails.navigation

import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.metadata
import com.donghanx.design.animation.enterTransitionHorizontal
import com.donghanx.design.animation.exitTransitionHorizontal
import com.donghanx.design.animation.fadeInTween
import com.donghanx.design.animation.fadeOutTween
import com.donghanx.design.composable.provider.NavAnimatedVisibilityProviderWrapper
import com.donghanx.model.CardPreview
import com.donghanx.navigation.NavKeyEntryProviderScope
import com.donghanx.navigation.Navigator
import com.donghanx.navigation.extensions.popTransition
import com.donghanx.navigation.extensions.transition
import com.donghanx.setdetails.SetDetailsScreen
import com.donghanx.setdetails.SetDetailsViewModel
import kotlinx.serialization.Serializable

@Serializable data class SetDetailsRoute(val setId: String, val parentRoute: String) : NavKey

fun Navigator.navigateToSetDetails(setId: String, parentRoute: String) {
    navigate(SetDetailsRoute(setId = setId, parentRoute = parentRoute))
}

fun NavKeyEntryProviderScope.setDetailsEntry(
    onBackClick: () -> Unit,
    onCardClick: (CardPreview) -> Unit,
    onTopBarVisibilityChanged: (isCollapsed: Boolean) -> Unit,
    onShowSnackbar: suspend (String) -> Unit,
) {
    entry<SetDetailsRoute>(
        metadata =
            metadata {
                transition { enterTransitionHorizontal() togetherWith fadeOutTween() }
                popTransition { fadeInTween() togetherWith exitTransitionHorizontal() }
            }
    ) { setDetailsRoute ->
        NavAnimatedVisibilityProviderWrapper {
            SetDetailsScreen(
                onBackClick = onBackClick,
                onCardClick = onCardClick,
                onTopBarVisibilityChanged = onTopBarVisibilityChanged,
                onShowSnackbar = onShowSnackbar,
                viewModel = setDetailsViewModel(setDetailsRoute),
            )
        }
    }
}

@Composable
private fun setDetailsViewModel(setDetailsRoute: SetDetailsRoute): SetDetailsViewModel =
    hiltViewModel<SetDetailsViewModel, SetDetailsViewModel.Factory>(
        creationCallback = { factory -> factory.create(setDetailsRoute) }
    )
