package com.donghanx.sets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.donghanx.design.ui.scrolltotop.ScrollToTopButton
import com.donghanx.model.SetInfo
import kotlinx.coroutines.launch

@Composable
fun SetsScreen(
    onShowSnackbar: suspend (message: String) -> Unit,
    viewModel: SetsViewModel = hiltViewModel()
) {
    val setsUiState by viewModel.setsUiState.collectAsStateWithLifecycle()

    when (val uiState = setsUiState) {
        is SetsUiState.Success -> SetsList(sets = uiState.sets)
        // TODO: add a placeholder composable for empty sets
        is SetsUiState.Empty -> Unit
    }

    LaunchedEffect(setsUiState.errorMessage) {
        if (setsUiState.hasError()) {
            onShowSnackbar(setsUiState.errorMessage())
        }
    }
}

@Composable
private fun SetsList(sets: List<SetInfo>) {
    Box(modifier = Modifier.fillMaxSize()) {
        val scope = rememberCoroutineScope()
        val lazyListState = rememberLazyListState()

        LazyColumn(state = lazyListState) {
            items(items = sets, key = { it.scryfallId }) { setInfo -> Text(text = setInfo.code) }
        }

        val shouldShowScrollToTopButton by remember {
            derivedStateOf { lazyListState.firstVisibleItemIndex > 0 }
        }
        ScrollToTopButton(
            visible = shouldShowScrollToTopButton,
            onClick = { scope.launch { lazyListState.animateScrollToItem(0) } },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
