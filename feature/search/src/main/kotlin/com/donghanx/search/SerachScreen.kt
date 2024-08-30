package com.donghanx.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.donghanx.design.R as DesignR
import com.donghanx.ui.SetInfoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchScreen(
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {

    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
        val searchUiState by viewModel.searchUiState.collectAsStateWithLifecycle()

        SearchBar(
            query = searchQuery,
            onQueryChange = viewModel::onSearchQueryChanged,
            onSearch = {},
            active = searchQuery.isNotEmpty(),
            onActiveChange = {},
            trailingIcon = {
                IconButton(onClick = onCloseClick) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = stringResource(id = DesignR.string.back),
                    )
                }
            },
        ) {
            when (val uiState = searchUiState) {
                is SearchUiState.Success -> {
                    LazyColumn {
                        items(uiState.searchedSets) {
                            SetInfoItem(code = it.code, name = it.name, iconUrl = it.iconSvgUri)
                        }
                    }
                }
                is SearchUiState.Empty ->
                    Text(text = stringResource(id = R.string.no_search_results))
            }
        }
    }
}
