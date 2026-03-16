package com.donghanx.nowinmtg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.donghanx.design.theme.NowInMTGTheme
import com.donghanx.nowinmtg.ui.NowInMtgApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val userPreference by viewModel.userPreference.collectAsStateWithLifecycle()
            val (themeConfig, darkModeConfig, contrastLevel) = userPreference

            NowInMTGTheme(
                useDarkMode = darkModeConfig.useDarkMode(isSystemInDarkTheme()),
                useDynamicColor = themeConfig.useDynamicColor,
                contrastLevel = contrastLevel,
            ) {
                NowInMtgApp(windowSizeClass = calculateWindowSizeClass(this))
            }
        }
    }
}