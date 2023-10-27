package com.donghanx.nowinmtg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.donghanx.nowinmtg.ui.NowInMtgApp
import com.donghanx.nowinmtg.ui.theme.NowInMTGTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { NowInMTGTheme { NowInMtgApp() } }
    }
}
