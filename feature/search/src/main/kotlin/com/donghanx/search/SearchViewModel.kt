package com.donghanx.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle) :
    ViewModel() {

    val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY_KEY, "")

    fun onSearchQueryChanged(searchQuery: String) {
        savedStateHandle[SEARCH_QUERY_KEY] = searchQuery
    }
}

private const val SEARCH_QUERY_KEY = "SearchQuery"
