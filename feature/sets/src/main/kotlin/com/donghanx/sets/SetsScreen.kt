package com.donghanx.sets

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SetsScreen(
    onShowSnackbar: suspend (message: String) -> Unit,
    viewModel: SetsViewModel = hiltViewModel()
) {
    Text(text = "Sets")
}
