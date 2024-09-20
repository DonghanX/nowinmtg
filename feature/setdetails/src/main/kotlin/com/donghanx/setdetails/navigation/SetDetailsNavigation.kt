package com.donghanx.setdetails.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.donghanx.setdetails.SetDetailsScreen

private const val SET_DETAILS_ROUTE = "SetDetails"
internal const val SET_CODE_ARGS = "SetCode"

fun NavController.navigateToSetDetails(code: String, parentRoute: String) {
    navigate("${setDetailsPrefixRoute(parentRoute)}/$code")
}

fun NavGraphBuilder.setDetailsScreen(parentRoute: String) {
    composable(route = "${setDetailsPrefixRoute(parentRoute)}/{$SET_CODE_ARGS}") {
        SetDetailsScreen()
    }
}

private fun setDetailsPrefixRoute(parentRoute: String): String = "${SET_DETAILS_ROUTE}_$parentRoute"
