package com.donghanx.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.donghanx.common.ErrorMessage
import com.donghanx.common.emptyErrorMessage
import com.donghanx.data.repository.sets.SetsRepository
import com.donghanx.domain.RefreshSetsUseCase
import com.donghanx.model.SetInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
internal class SearchViewModel
@Inject
constructor(
    private val setsRepository: SetsRepository,
    refreshSetsIfNeeded: RefreshSetsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY_KEY, "")

    val searchUiState: StateFlow<SearchUiState> =
        searchQuery
            .filter { it.length >= MIN_SEARCH_QUERY_LENGTH }
            .debounce(timeoutMillis = SEARCH_DEBOUNCE_MILLIS)
            .flatMapLatest { query ->
                setsRepository.searchAllSetsByQuery(query).map { it.toSearchUiState() }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(DEFAULT_STOP_TIME_MILLIS),
                initialValue = SearchUiState.Empty(refreshing = true)
            )

    init {
        viewModelScope.launch { refreshSetsIfNeeded().collect() }
    }

    fun onSearchQueryChanged(searchQuery: String) {
        savedStateHandle[SEARCH_QUERY_KEY] = searchQuery
    }

    private fun List<SetInfo>.toSearchUiState(): SearchUiState {
        return if (isNotEmpty()) SearchUiState.Success(this, refreshing = false)
        else SearchUiState.Empty(refreshing = false)
    }
}

internal sealed interface SearchUiState {
    val refreshing: Boolean
    val errorMessage: ErrorMessage

    data class Success(
        val searchedSets: List<SetInfo>,
        override val refreshing: Boolean,
        override val errorMessage: ErrorMessage = emptyErrorMessage()
    ) : SearchUiState

    data class Empty(
        override val refreshing: Boolean,
        override val errorMessage: ErrorMessage = emptyErrorMessage()
    ) : SearchUiState
}

private const val SEARCH_QUERY_KEY = "SearchQuery"
private const val SEARCH_DEBOUNCE_MILLIS = 500L
private const val MIN_SEARCH_QUERY_LENGTH = 3
private const val DEFAULT_STOP_TIME_MILLIS = 5_000L
