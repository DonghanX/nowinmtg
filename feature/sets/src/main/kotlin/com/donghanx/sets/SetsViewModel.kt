package com.donghanx.sets

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.donghanx.common.ErrorMessage
import com.donghanx.common.NetworkResult
import com.donghanx.common.asErrorMessage
import com.donghanx.common.emptyErrorMessage
import com.donghanx.common.extensions.filterAll
import com.donghanx.common.utils.DateMillisRange
import com.donghanx.common.utils.epochMilliOfDate
import com.donghanx.common.utils.yearOfDate
import com.donghanx.data.repository.sets.SetsRepository
import com.donghanx.domain.RefreshSetsUseCase
import com.donghanx.model.SetInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class SetsViewModel
@Inject
constructor(
    private val setsRepository: SetsRepository,
    private val refreshSetsIfNeeded: RefreshSetsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val viewModelState = MutableStateFlow(SetsViewModelState(refreshing = false))

    val setsUiState: StateFlow<SetsUiState> =
        viewModelState
            .map(SetsViewModelState::toUiState)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(DEFAULT_STOP_TIME_MILLIS),
                initialValue = viewModelState.value.toUiState()
            )

    val setTypeQuery: StateFlow<String?> =
        savedStateHandle.getStateFlow(key = SET_TYPE_KEY, initialValue = null)

    val startMillisQuery: StateFlow<Long?> =
        savedStateHandle.getStateFlow(key = START_DATE_MILLIS_KEY, initialValue = null)

    val endMillisQuery: StateFlow<Long?> =
        savedStateHandle.getStateFlow(key = END_DATE_MILLIS_KEY, initialValue = null)

    private val setsQuery: Flow<SetsQuery> =
        combine(setTypeQuery, startMillisQuery, endMillisQuery) { setType, startMillis, endMillis ->
                SetsQuery(setType = setType, dateRange = DateMillisRange(startMillis, endMillis))
            }
            .onEach { viewModelState.update { it.copy(refreshing = true) } }

    init {
        observeSets()
        refreshSets()
    }

    private fun observeSets() {
        viewModelScope.launch {
            combine(setsRepository.getAllSets(), setsQuery) { sets, setsQuery ->
                    sets.filteredByQuery(query = setsQuery)
                }
                .map { sets -> sets.groupBy { it.releasedAt.yearOfDate() } }
                .onEach { groupedSets ->
                    viewModelState.update { it.copy(groupedSets = groupedSets, refreshing = false) }
                }
                .collect()
        }
    }

    fun refreshSets(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            refreshSetsIfNeeded(forceRefresh)
                .onStart { viewModelState.update { it.copy(refreshing = true) } }
                .onEach { it.updateViewModelState() }
                .collect()
        }
    }

    private fun List<SetInfo>.filteredByQuery(query: SetsQuery): List<SetInfo> {
        viewModelState.update { it.copy(refreshing = true) }

        return if (query.isEmpty()) this
        else
            filterAll(
                { query.setType == null || it.setType == query.setType },
                { query.dateRange.contains(it.releasedAt.epochMilliOfDate(RELEASE_DATE_OFFSET)) }
            )
    }

    private fun <T> NetworkResult<T>.updateViewModelState() {
        viewModelState.update { prevState ->
            when (this) {
                is NetworkResult.Success ->
                    prevState.copy(refreshing = false, errorMessage = emptyErrorMessage())
                is NetworkResult.Error ->
                    prevState.copy(
                        refreshing = false,
                        errorMessage = exception.asErrorMessage(id = prevState.errorMessage.id + 1)
                    )
            }
        }
    }

    fun onSelectedSetTypeChanged(setType: String?) {
        savedStateHandle[SET_TYPE_KEY] = setType
    }

    fun onDateRangeSelected(startDateMillis: Long?, endDateMillis: Long?) {
        savedStateHandle[START_DATE_MILLIS_KEY] = startDateMillis
        savedStateHandle[END_DATE_MILLIS_KEY] = endDateMillis
    }
}

internal sealed interface SetsUiState {
    val refreshing: Boolean
    val errorMessage: ErrorMessage

    fun hasError(): Boolean = errorMessage.hasError

    data class Success(
        val groupedSets: SetInfoMap,
        override val refreshing: Boolean,
        override val errorMessage: ErrorMessage = emptyErrorMessage()
    ) : SetsUiState

    data class Empty(
        override val refreshing: Boolean,
        override val errorMessage: ErrorMessage = emptyErrorMessage()
    ) : SetsUiState
}

private data class SetsViewModelState(
    val groupedSets: SetInfoMap = emptyMap(),
    val refreshing: Boolean,
    val errorMessage: ErrorMessage = emptyErrorMessage()
) {
    fun toUiState(): SetsUiState =
        when {
            groupedSets.isNotEmpty() ->
                SetsUiState.Success(
                    groupedSets = groupedSets,
                    refreshing = refreshing,
                    errorMessage = errorMessage
                )
            else -> SetsUiState.Empty(refreshing = refreshing, errorMessage = errorMessage)
        }
}

private data class SetsQuery(
    val setType: String? = null,
    val dateRange: DateMillisRange = DateMillisRange.EMPTY
) {
    fun isEmpty(): Boolean = setType == null && dateRange.isEmpty()

    fun isNotEmpty(): Boolean = !isNotEmpty()
}

// The date the set was released is in GMT-8 Pacific time
const val RELEASE_DATE_OFFSET = 8
private const val DEFAULT_STOP_TIME_MILLIS = 5_000L
private const val SET_TYPE_KEY = "SetType"
private const val START_DATE_MILLIS_KEY = "StartDateMillis"
private const val END_DATE_MILLIS_KEY = "EndDateMillis"

/**
 * A typealias of the Map that uses the year String as the key and the [SetInfo] list as the value
 */
private typealias SetInfoMap = Map<Int, List<SetInfo>>
