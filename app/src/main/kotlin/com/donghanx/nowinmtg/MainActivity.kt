package com.donghanx.nowinmtg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.donghanx.nowinmtg.ui.theme.NowInMTGTheme
import com.donghanx.randomcards.DefaultCardsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NowInMTGTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val snackbarHostState = remember { SnackbarHostState() }
                    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { paddingValues ->
                        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                            DefaultCardsScreen(
                                onShowSnackbar = { message ->
                                    snackbarHostState.showSnackbar(
                                        message = message,
                                        withDismissAction = true
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
