package com.donghanx.setdetails.navigation

import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.donghanx.design.composable.provider.LocalNavAnimatedVisibilityScope
import com.donghanx.model.CardPreview
import com.donghanx.setdetails.SetDetailsScreen

const val SET_DETAILS_ROUTE = "SetDetails"
internal const val SET_ID_ARGS = "SetId"

fun NavController.navigateToSetDetails(setId: String, parentRoute: String) {
    navigate("${setDetailsPrefixRoute(parentRoute)}/$setId")
}

fun NavGraphBuilder.setDetailsScreen(
    parentRoute: String,
    onBackClick: () -> Unit,
    onCardClick: (CardPreview) -> Unit,
) {
    composable(route = "${setDetailsPrefixRoute(parentRoute)}/{$SET_ID_ARGS}") {
        CompositionLocalProvider(LocalNavAnimatedVisibilityScope provides this) {
            SetDetailsScreen(onBackClick = onBackClick, onCardClick = onCardClick)
        }
    }
}

private fun setDetailsPrefixRoute(parentRoute: String): String = "${SET_DETAILS_ROUTE}_$parentRoute"
