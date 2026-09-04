package com.donghanx.setdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.donghanx.common.extensions.toDisplayName
import com.donghanx.common.utils.dayMonthYearOfDate
import com.donghanx.design.R as DesignR
import com.donghanx.design.composable.extensions.hasEnoughItemsToScroll
import com.donghanx.design.composable.extensions.toDp
import com.donghanx.design.composable.provider.SharedTransitionProviderPreviewWrapper
import com.donghanx.design.theme.NowInMTGTheme
import com.donghanx.design.ui.appbar.CollapsingNestedScrollConnection
import com.donghanx.design.ui.appbar.rememberCollapsingNestedScrollConnection
import com.donghanx.design.ui.placeholder.EmptyScreenWithIcon
import com.donghanx.design.ui.text.ResizableText
import com.donghanx.mock.MockUtils
import com.donghanx.model.CardPreview
import com.donghanx.model.SetInfo
import com.donghanx.navigation.navkey.routeName
import com.donghanx.setdetails.navigation.SetDetailsRoute
import com.donghanx.ui.CardsGallery
import com.donghanx.ui.SetMetaDataRow
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun SetDetailsScreen(
    onBackClick: () -> Unit,
    onCardClick: (CardPreview) -> Unit,
    onTopBarVisibilityChanged: (isCollapsed: Boolean) -> Unit,
    onShowSnackbar: suspend (String) -> Unit,
    viewModel: SetDetailsViewModel,
) {
    val uiState by viewModel.setDetailsUiStateFlow.collectAsStateWithLifecycle()

    SetDetailsScreen(
        setDetailsUiState = uiState,
        onBackClick = onBackClick,
        onCardClick = onCardClick,
        onTopBarVisibilityChanged = onTopBarVisibilityChanged,
        onShowSnackbar = onShowSnackbar,
    )
}

@Composable
private fun SetDetailsScreen(
    setDetailsUiState: SetDetailsUiState,
    onBackClick: () -> Unit,
    onCardClick: (CardPreview) -> Unit,
    onTopBarVisibilityChanged: (isCollapsed: Boolean) -> Unit,
    onShowSnackbar: suspend (String) -> Unit,
) {
    val lazyGridState = rememberLazyGridState()
    val isCardsGridScrollable by remember {
        derivedStateOf { lazyGridState.hasEnoughItemsToScroll() }
    }

    val density = LocalDensity.current

    val appBarMaxHeightPx =
        with(density) { appBarHeight.roundToPx() + WindowInsets.systemBars.getTop(this) }
    val nestedScrollConnection = rememberCollapsingNestedScrollConnection(appBarMaxHeightPx)

    val spaceHeight by
        remember(density, isCardsGridScrollable) {
            derivedStateOf {
                val offset =
                    nestedScrollConnection.targetOffset.takeIf { isCardsGridScrollable } ?: 0
                (appBarMaxHeightPx + offset).toDp(density)
            }
        }

    TopBarScrollSyncEffect(
        appBarMaxHeightPx = appBarMaxHeightPx,
        nestedScrollConnection = nestedScrollConnection,
        onTopBarVisibilityChanged = onTopBarVisibilityChanged,
    )

    // TODO: Fix scroll flickering for short content lists where the scrollable range is
    //  smaller than the SetDetailsHeader height.
    Box(
        modifier =
            Modifier.fillMaxSize().nestedScroll(connection = nestedScrollConnection).clipToBounds()
    ) {
        val errorMessage = setDetailsUiState.errorMessage
        LaunchedEffect(errorMessage) {
            if (errorMessage.hasError) {
                onShowSnackbar(errorMessage.message)
            }
        }

        when {
            setDetailsUiState.cards.isNotEmpty() -> {
                Column {
                    // A spacer that syncs with SetDetailsHeader’s offset to enable smooth
                    // collapsing behavior.
                    // While the header is expanding or collapsing, this spacer adjusts its height
                    // to keep the list static, ensuring there’s no visual gap between the LazyGrid
                    // and the header during scroll transitions.
                    Spacer(modifier = Modifier.height(spaceHeight))

                    CardsGalleryInSet(
                        cardsInSet = setDetailsUiState.cards,
                        onCardClick = onCardClick,
                        lazyGridState = lazyGridState,
                        onScrollToTop = { nestedScrollConnection.reset() },
                    )
                }
            }

            setDetailsUiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center).padding(top = appBarHeight)
                )
            }

            else -> {
                val currentSetName =
                    setDetailsUiState.setInfo?.name ?: stringResource(R.string.this_set)
                EmptyScreenWithIcon(
                    text = stringResource(R.string.no_cards_in_this_set, currentSetName),
                    imageVector = Icons.Default.Warning,
                )
            }
        }

        SetDetailsHeader(
            setInfo = setDetailsUiState.setInfo,
            onBackClick = onBackClick,
            modifier =
                Modifier.offset {
                    if (isCardsGridScrollable) IntOffset(0, nestedScrollConnection.targetOffset)
                    else IntOffset.Zero
                },
        )
    }
}

@Composable
private fun SetDetailsHeader(
    setInfo: SetInfo?,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.background(color = MaterialTheme.colorScheme.background)) {
        Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.systemBars))

        SetDetailsTopBar(setInfo = setInfo, onBackClick = onBackClick)

        setInfo?.let {
            SetMetaDataRow(
                code = it.code,
                setType = it.setType,
                cardCount = it.cardCount,
                releasedDate = it.releasedAt.dayMonthYearOfDate(),
                modifier =
                    Modifier.padding(top = 2.dp, bottom = 8.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                contentTextStyle = MaterialTheme.typography.bodyMedium,
                chipTextStyle = MaterialTheme.typography.labelMedium,
            )
        }

        HorizontalDivider(modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
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
                contentDescription = stringResource(id = DesignR.string.back),
            )
        }

        setInfo?.let {
            SetDetailsTitle(
                name = it.name.toDisplayName(),
                iconUri = it.iconSvgUri,
                modifier = Modifier.weight(1F),
            )
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
        AsyncImage(
            model = iconUri,
            contentDescription = name,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSurface),
            modifier = Modifier.size(24.dp),
        )

        Spacer(modifier = Modifier.width(4.dp))

        ResizableText(text = name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun CardsGalleryInSet(
    cardsInSet: ImmutableList<CardPreview>,
    onCardClick: (CardPreview) -> Unit,
    lazyGridState: LazyGridState,
    modifier: Modifier = Modifier,
    onScrollToTop: () -> Unit = {},
) {
    CardsGallery(
        parentRoute = SetDetailsRoute::class.routeName,
        cards = cardsInSet,
        onCardClick = onCardClick,
        contentPadding = PaddingValues(all = 4.dp),
        lazyGridState = lazyGridState,
        onScrollToTop = onScrollToTop,
        modifier = modifier,
    )
}

@Composable
private fun TopBarScrollSyncEffect(
    appBarMaxHeightPx: Int,
    nestedScrollConnection: CollapsingNestedScrollConnection,
    onTopBarVisibilityChanged: (isCollapsed: Boolean) -> Unit,
) {
    val collapsedFraction by
        remember(appBarMaxHeightPx) {
            derivedStateOf { nestedScrollConnection.absoluteTargetOffset / appBarMaxHeightPx }
        }
    val isTopbarCollapsed by remember { derivedStateOf { collapsedFraction > 0.5F } }

    LaunchedEffect(isTopbarCollapsed) { onTopBarVisibilityChanged(isTopbarCollapsed) }
}

@PreviewLightDark
@Composable
private fun SetDetailsScreenPreview() {
    SharedTransitionProviderPreviewWrapper {
        NowInMTGTheme {
            SetDetailsScreen(
                setDetailsUiState =
                    SetDetailsUiState(
                        cards = MockUtils.emptyCards.toImmutableList(),
                        setInfo = MockUtils.soiExpansion,
                        isLoading = false,
                    ),
                onBackClick = {},
                onCardClick = {},
                onTopBarVisibilityChanged = {},
                onShowSnackbar = {},
            )
        }
    }
}

private val appBarHeight = 104.dp
