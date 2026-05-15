package com.donghanx.settings.navigation

import androidx.compose.ui.window.DialogProperties
import androidx.navigation3.scene.DialogSceneStrategy
import com.donghanx.navigation.NavKeyEntryProviderScope
import com.donghanx.navigation.Navigator
import com.donghanx.navigation.navkey.DialogNavKey
import com.donghanx.settings.SettingsDialog
import kotlinx.serialization.Serializable

@Serializable data object SettingsRoute : DialogNavKey

fun Navigator.navigateToSettings() {
    navigate(SettingsRoute)
}

fun NavKeyEntryProviderScope.settingsDialogEntry() {
    entry<SettingsRoute>(
        metadata = DialogSceneStrategy.dialog(DialogProperties(usePlatformDefaultWidth = true))
    ) {
        SettingsDialog()
    }
}
