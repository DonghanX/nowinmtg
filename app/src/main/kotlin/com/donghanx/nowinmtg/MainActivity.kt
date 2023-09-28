package com.donghanx.nowinmtg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.donghanx.nowinmtg.ui.theme.NowInMTGTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NowInMTGTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DefaultCardsScreen()
                }
            }
        }
    }
}

@Composable
fun DefaultCardsScreen(modifier: Modifier = Modifier, viewModel: MainViewModel = viewModel()) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val defaultCardsUiState by viewModel.defaultCardsStateFlow.collectAsState()

        when (val uiState = defaultCardsUiState) {
            is DefaultCardsUiState.Success -> {
                LazyColumn {
                    items(uiState.cards, key = { it.id }) { card -> Text(text = card.name) }
                }
            }
            is DefaultCardsUiState.Error -> {
                LaunchedEffect(uiState) { uiState.exception?.printStackTrace() }
                Snackbar { Text(text = "Error message: ${uiState.exception?.message.orEmpty()}") }
            }
            DefaultCardsUiState.Loading -> {
                CircularProgressIndicator()
            }
        }
    }
}
