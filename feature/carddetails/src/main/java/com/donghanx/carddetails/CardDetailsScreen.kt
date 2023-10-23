package com.donghanx.carddetails

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    val cardDetailsUiState by viewModel.cardDetailsUiState.collectAsStateWithLifecycle()

    when (val uiState = cardDetailsUiState) {
        is CardDetailsUiState.Success -> {
            Text(text = "Success: card name is ${uiState.cardDetails.name}")
        }
        is CardDetailsUiState.NoCardDetails -> {
            Text(text = "Empty")
        }
    }

    LaunchedEffect(cardDetailsUiState.errorMessage) {
        if (cardDetailsUiState.hasError()) {
            onShowSnackbar(cardDetailsUiState.errorMessage.message)
        }
    }
}
