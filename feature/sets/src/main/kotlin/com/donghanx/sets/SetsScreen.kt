package com.donghanx.sets

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.donghanx.design.ui.pullrefresh.PullRefreshIndicator
import com.donghanx.design.ui.pullrefresh.pullRefresh
import com.donghanx.design.ui.pullrefresh.rememberPullRefreshState
import com.donghanx.design.ui.scrolltotop.ScrollToTopButton
import com.donghanx.model.SetInfo
import kotlinx.coroutines.launch

@Composable
fun SetsScreen(
    onShowSnackbar: suspend (message: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SetsViewModel = hiltViewModel()
) {
    val setsUiState by viewModel.setsUiState.collectAsStateWithLifecycle()
    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = setsUiState.refreshing,
            onRefresh = { viewModel.refreshSets() }
        )

    Box(modifier = modifier.fillMaxSize().pullRefresh(state = pullRefreshState)) {
        when (val uiState = setsUiState) {
            is SetsUiState.Success ->
                Column(modifier = Modifier.fillMaxSize()) {
                    SetsFilterRow(
                        selectedSetType = viewModel.getSelectedSetType(),
                        onSetTypeChanged = viewModel::onSelectedSetTypeChanged,
                        onSetTypeReset = viewModel::onSelectedSetTypeReset
                    )
                    SetsList(sets = uiState.sets)
                }

            // TODO: add a placeholder composable for empty sets
            is SetsUiState.Empty -> Unit
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

@Composable
private fun SetsList(sets: List<SetInfo>) {
    Box(modifier = Modifier.fillMaxSize()) {
        val scope = rememberCoroutineScope()
        val lazyListState = rememberLazyListState()

        LazyColumn(
            state = lazyListState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(items = sets, key = { it.scryfallId }) {
                SetInfoItem(code = it.code, name = it.name, iconUrl = it.iconSvgUri)
            }
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

@Composable
private fun SetsFilterRow(
    modifier: Modifier = Modifier,
    selectedSetType: String?,
    onSetTypeChanged: (setType: String?) -> Unit,
    onSetTypeReset: () -> Unit
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(all = 6.dp)
                .horizontalScroll(state = rememberScrollState())
    ) {
        SetTypeFilter(
            selectedSetType = selectedSetType,
            onSetTypeChanged = onSetTypeChanged,
            onSetTypeReset = onSetTypeReset
        )
    }
}
