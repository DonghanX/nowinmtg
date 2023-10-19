package com.donghanx.carddetails

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CardDetailsScreen(
    onShowSnackbar: suspend (message: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CardDetailsViewModel = hiltViewModel()
) {
    val id by viewModel.cardId.collectAsStateWithLifecycle()
    Text(text = "Card Detail: $id")
}
