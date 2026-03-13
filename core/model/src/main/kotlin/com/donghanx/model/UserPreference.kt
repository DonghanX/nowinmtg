package com.donghanx.model

import kotlinx.serialization.Serializable

@Serializable
data class UserPreference(
    val themeConfig: ThemeConfig = ThemeConfig.DYNAMIC_COLOR,
    val darkModeConfig: DarkModeConfig = DarkModeConfig.SYSTEM_DEFAULT,
)

enum class ThemeConfig {
    DYNAMIC_COLOR,
    DEFAULT,
}

enum class DarkModeConfig {
    SYSTEM_DEFAULT,
    LIGHT,
    DARK,
}
