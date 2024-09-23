package com.donghanx.setdetails.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.donghanx.setdetails.SetDetailsScreen

private const val SET_DETAILS_ROUTE = "SetDetails"
internal const val SET_ID_ARGS = "SetId"

fun NavController.navigateToSetDetails(setId: String, parentRoute: String) {
    navigate("${setDetailsPrefixRoute(parentRoute)}/$setId")
}

fun NavGraphBuilder.setDetailsScreen(parentRoute: String, onBackClick: () -> Unit) {
    composable(route = "${setDetailsPrefixRoute(parentRoute)}/{$SET_ID_ARGS}") {
        SetDetailsScreen(onBackClick = onBackClick)
    }
}

private fun setDetailsPrefixRoute(parentRoute: String): String = "${SET_DETAILS_ROUTE}_$parentRoute"
