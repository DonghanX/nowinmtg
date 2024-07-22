package com.donghanx.sets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.donghanx.common.utils.DateMillisRange
import com.donghanx.design.composable.extensions.isFirstItemNotVisible
import com.donghanx.design.ui.pullrefresh.PullRefreshIndicator
import com.donghanx.design.ui.pullrefresh.pullRefresh
import com.donghanx.design.ui.pullrefresh.rememberPullRefreshState
import com.donghanx.design.ui.scrolltotop.ScrollToTopButton
import com.donghanx.model.SetInfo
import com.donghanx.sets.preview.SetsListPreviewParameterProvider
import com.donghanx.ui.SetInfoItem
import com.donghanx.ui.StickyYearReleased
import kotlinx.coroutines.launch

@Composable
internal fun SetsScreen(
    onShowSnackbar: suspend (message: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SetsViewModel = hiltViewModel()
) {
    val setsUiState by viewModel.setsUiState.collectAsStateWithLifecycle()
    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = setsUiState.refreshing,
            onRefresh = { viewModel.refreshSets(forceRefresh = true) }
        )

    Box(modifier = modifier.fillMaxSize().pullRefresh(state = pullRefreshState)) {
        Column(modifier = Modifier.fillMaxSize()) {
            val selectedSetType by viewModel.setTypeQuery.collectAsStateWithLifecycle()
            val selectedStartMillis by viewModel.startMillisQuery.collectAsStateWithLifecycle()
            val selectedEndMillis by viewModel.endMillisQuery.collectAsStateWithLifecycle()

            SetsFilterRow(
                selectedSetType = selectedSetType,
                onSetTypeChanged = viewModel::onSelectedSetTypeChanged,
                selectedDateMillisRange = DateMillisRange(selectedStartMillis, selectedEndMillis),
                onDateRangeSelected = viewModel::onDateRangeSelected
            )

            when (val uiState = setsUiState) {
                is SetsUiState.Success -> SetsList(groupedSets = uiState.groupedSets)

                // TODO: add a placeholder composable for empty sets
                is SetsUiState.Empty -> Unit
            }
        }

        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = setsUiState.refreshing,
            state = pullRefreshState
        )

        LaunchedEffect(setsUiState.errorMessage) {
            if (setsUiState.hasError()) {
                onShowSnackbar(setsUiState.errorMessage())
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SetsList(groupedSets: Map<Int, List<SetInfo>>) {
    Box(modifier = Modifier.fillMaxSize()) {
        val scope = rememberCoroutineScope()
        val lazyListState = rememberLazyListState()

        LazyColumn(
            state = lazyListState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            groupedSets.forEach { (yearReleased, sets) ->
                stickyHeader(key = yearReleased) {
                    StickyYearReleased(
                        yearReleased = yearReleased,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    )
                }

                items(items = sets, key = { it.scryfallId }) {
                    SetInfoItem(
                        code = it.code,
                        name = it.name,
                        iconUrl = it.iconSvgUri,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp)
                    )
                }
            }
        }

        val shouldShowScrollToTopButton by remember {
            derivedStateOf { lazyListState.isFirstItemNotVisible() }
        }
        ScrollToTopButton(
            visible = shouldShowScrollToTopButton,
            onClick = { scope.launch { lazyListState.animateScrollToItem(0) } },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun SetsFilterRow(
    modifier: Modifier = Modifier,
    selectedSetType: String?,
    selectedDateMillisRange: DateMillisRange,
    onSetTypeChanged: (setType: String?) -> Unit,
    onDateRangeSelected: (startDateMillis: Long?, endDateMillis: Long?) -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp)
                .horizontalScroll(state = rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(space = 6.dp)
    ) {
        SetTypeFilter(selectedSetType = selectedSetType, onSetTypeChanged = onSetTypeChanged)

        ReleaseDateFilter(
            selectedDateMillisRange = selectedDateMillisRange,
            onDateRangeSelected = onDateRangeSelected
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun SetsListPreview(
    @PreviewParameter(SetsListPreviewParameterProvider::class) groupedSets: Map<Int, List<SetInfo>>
) {
    SetsList(groupedSets = groupedSets)
}
