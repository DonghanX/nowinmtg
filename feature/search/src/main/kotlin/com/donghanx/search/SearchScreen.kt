package com.donghanx.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.donghanx.design.R as DesignR
import com.donghanx.mock.MockUtils
import com.donghanx.model.SetInfo
import com.donghanx.ui.SetInfoItem

@Composable
internal fun SearchScreen(
    onCloseClick: () -> Unit,
    onSetClick: (SetInfo) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {

    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
        val searchUiState by viewModel.searchUiState.collectAsStateWithLifecycle()

        SearchScreen(
            searchQuery = searchQuery,
            searchUiState = searchUiState,
            onSearchQueryChanged = viewModel::onSearchQueryChanged,
            onCloseClick = onCloseClick,
            onSetClick = onSetClick,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen(
    searchQuery: String,
    searchUiState: SearchUiState,
    onSearchQueryChanged: (String) -> Unit,
    onCloseClick: () -> Unit,
    onSetClick: (SetInfo) -> Unit,
) {
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = searchQuery,
                onQueryChange = onSearchQueryChanged,
                onSearch = {},
                expanded = searchQuery.length >= MIN_SEARCH_QUERY_LENGTH,
                onExpandedChange = {},
                trailingIcon = {
                    IconButton(onClick = onCloseClick) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = stringResource(id = DesignR.string.back),
                        )
                    }
                },
            )
        },
        expanded = searchQuery.length >= MIN_SEARCH_QUERY_LENGTH,
        onExpandedChange = {},
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(top = 8.dp, start = 4.dp)) {
            when (searchUiState) {
                is SearchUiState.Success -> {
                    LazyColumn {
                        items(searchUiState.searchedSets) {
                            SetInfoItem(
                                code = it.code,
                                name = it.name,
                                iconUrl = it.iconSvgUri,
                                onClick = { onSetClick(it) },
                            )
                        }
                    }
                }
                is SearchUiState.Empty ->
                    Text(text = stringResource(id = R.string.no_search_results))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenActivePreview(
    @PreviewParameter(SearchScreenPreviewParameter::class) searchUiState: SearchUiState
) {
    SearchScreen(
        searchQuery = "search query",
        searchUiState = searchUiState,
        onSearchQueryChanged = {},
        onCloseClick = {},
        onSetClick = {},
    )
}

@Preview
@Composable
private fun SearchScreenInactivePreview() {
    SearchScreen(
        searchQuery = "",
        searchUiState = SearchUiState.Empty(refreshing = false),
        onSearchQueryChanged = {},
        onCloseClick = {},
        onSetClick = {},
    )
}

private class SearchScreenPreviewParameter : PreviewParameterProvider<SearchUiState> {
    override val values: Sequence<SearchUiState>
        get() =
            sequenceOf(
                SearchUiState.Success(
                    searchedSets = listOf(MockUtils.soiExpansion, MockUtils.xlnExpansion),
                    refreshing = false,
                ),
                SearchUiState.Empty(refreshing = false),
            )
}
