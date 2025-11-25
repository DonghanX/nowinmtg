package com.donghanx.settings.navigation

import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import com.donghanx.settings.SettingsDialog
import kotlinx.serialization.Serializable

@Serializable data object SettingsRoute

fun NavController.navigateToSettings() {
    navigate(SettingsRoute) { launchSingleTop = true }
}

fun NavGraphBuilder.settingsDialog() {
    dialog<SettingsRoute>(dialogProperties = DialogProperties(usePlatformDefaultWidth = true)) {
        SettingsDialog()
    }
}
