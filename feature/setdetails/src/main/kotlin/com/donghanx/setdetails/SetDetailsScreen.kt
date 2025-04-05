package com.donghanx.setdetails

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.donghanx.design.R
import com.donghanx.design.composable.provider.SharedTransitionProviderWrapper
import com.donghanx.design.ui.grid.fullWidthItem
import com.donghanx.design.ui.text.ResizableText
import com.donghanx.mock.MockUtils
import com.donghanx.model.CardPreview
import com.donghanx.model.SetInfo
import com.donghanx.setdetails.navigation.SET_DETAILS_ROUTE
import com.donghanx.ui.CardsGallery

@Composable
fun SetDetailsScreen(
    onBackClick: () -> Unit,
    onCardClick: (CardPreview) -> Unit,
    viewModel: SetDetailsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.setDetailsUiState.collectAsStateWithLifecycle()

    SetDetailsScreen(
        setDetailsUiState = uiState,
        onBackClick = onBackClick,
        onCardClick = onCardClick,
    )
}

@Composable
private fun SetDetailsScreen(
    setDetailsUiState: SetDetailsUiState,
    onBackClick: () -> Unit,
    onCardClick: (CardPreview) -> Unit,
) {
    Column {
        SetDetailsTopBar(setInfo = setDetailsUiState.setInfo, onBackClick = onBackClick)

        SetDetailsBasicInfo(
            setInfo = setDetailsUiState.setInfo,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp, horizontal = 4.dp))

        Box(modifier = Modifier.fillMaxSize()) {
            when (setDetailsUiState) {
                is SetDetailsUiState.Success -> {
                    CardsGalleryInSet(
                        cardsInSet = setDetailsUiState.cards,
                        releasedAt = setDetailsUiState.setInfo?.releasedAt,
                        onCardClick = onCardClick,
                    )
                }
                is SetDetailsUiState.NoSetDetails -> {}
                is SetDetailsUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@Composable
private fun SetDetailsBasicInfo(setInfo: SetInfo?, modifier: Modifier = Modifier) {
    setInfo?.let {
        Row(
            modifier = modifier.horizontalScroll(state = rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(space = 6.dp),
        ) {
            SuggestionChip(onClick = {}, label = { Text("type: ${it.setType}") })

            SuggestionChip(onClick = {}, label = { Text("code: ${it.code}") })

            SuggestionChip(onClick = {}, label = { Text("count: ${it.cardCount}") })
        }
    }
}

@Composable
private fun SetDetailsTopBar(
    setInfo: SetInfo?,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth().padding(bottom = 2.dp),
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.back),
            )
        }

        setInfo?.let {
            SetDetailsTitle(name = it.name, iconUri = it.iconSvgUri, modifier = Modifier.weight(1F))
        }

        Spacer(modifier = Modifier.size(40.dp))
    }
}

@Composable
private fun SetDetailsTitle(name: String, iconUri: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        AsyncImage(model = iconUri, contentDescription = name, modifier = Modifier.size(24.dp))

        Spacer(modifier = Modifier.width(4.dp))

        ResizableText(text = name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun CardsGalleryInSet(
    cardsInSet: List<CardPreview>,
    releasedAt: String?,
    onCardClick: (CardPreview) -> Unit,
    modifier: Modifier = Modifier,
) {
    CardsGallery(
        parentRoute = SET_DETAILS_ROUTE,
        cards = cardsInSet,
        onCardClick = onCardClick,
        header = {
            fullWidthItem {
                Text(
                    text = "Released at $releasedAt",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Medium,
                )
            }
        },
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
private fun SetDetailsScreenPreview() {
    SharedTransitionProviderWrapper {
        SetDetailsScreen(
            setDetailsUiState =
                SetDetailsUiState.Success(
                    cards = MockUtils.emptyCards,
                    setInfo = MockUtils.soiExpansion,
                ),
            onBackClick = {},
            onCardClick = {},
        )
    }
}
