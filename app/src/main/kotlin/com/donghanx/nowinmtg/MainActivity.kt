package com.donghanx.nowinmtg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.donghanx.design.pullrefresh.PullRefreshIndicator
import com.donghanx.design.pullrefresh.pullRefresh
import com.donghanx.design.pullrefresh.rememberPullRefreshState
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
    val refreshing by viewModel.refreshing.collectAsState()
    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = refreshing,
            onRefresh = { viewModel.refreshRandomCards() }
        )

    Box(
        modifier = modifier.fillMaxSize().pullRefresh(state = pullRefreshState),
        contentAlignment = Alignment.Center
    ) {
        val defaultCardsUiState by viewModel.randomCardsUiState.collectAsState()

        when (val uiState = defaultCardsUiState) {
            is RandomCardsUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(uiState.cards, key = { it.id }) { card ->
                        Text(text = card.name, textAlign = TextAlign.Center)
                    }
                }
            }
            is RandomCardsUiState.Error -> {
                LaunchedEffect(uiState) { uiState.exception?.printStackTrace() }
                Snackbar { Text(text = "Error message: ${uiState.exception?.message.orEmpty()}") }
            }
            RandomCardsUiState.Loading -> {
                CircularProgressIndicator()
            }
        }

        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = refreshing,
            state = pullRefreshState
        )
    }
}
