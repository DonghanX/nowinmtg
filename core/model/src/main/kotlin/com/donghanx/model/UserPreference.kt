package com.donghanx.model

import kotlinx.serialization.Serializable

@Serializable
data class UserPreference(
    val themeConfig: ThemeConfig = ThemeConfig.DYNAMIC_COLOR,
    val darkModeConfig: DarkModeConfig = DarkModeConfig.SYSTEM_DEFAULT,
)

enum class ThemeConfig {
    DYNAMIC_COLOR,
    DEFAULT;

    val useDynamicColor: Boolean
        get() = this == DYNAMIC_COLOR
}

enum class DarkModeConfig {
    SYSTEM_DEFAULT,
    LIGHT,
    DARK;

    fun useDarkMode(isSystemDarkTheme: Boolean): Boolean =
        isSystemDarkTheme && this == SYSTEM_DEFAULT || this == DARK
}
