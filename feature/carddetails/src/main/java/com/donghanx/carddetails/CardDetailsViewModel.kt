package com.donghanx.carddetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.donghanx.carddetails.navigation.CARD_ID_ARGS
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class CardDetailsViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {

    val cardId: StateFlow<String?> =
        savedStateHandle.getStateFlow(key = CARD_ID_ARGS, initialValue = null)
}
