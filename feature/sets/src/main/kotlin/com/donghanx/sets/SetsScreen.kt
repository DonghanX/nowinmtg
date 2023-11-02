package com.donghanx.sets

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.donghanx.model.SetInfo

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
    LazyColumn { items(sets, key = { it.scryfallId }) { setInfo -> Text(text = setInfo.code) } }
}
