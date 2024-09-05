package com.donghanx.domain

import com.donghanx.common.INVALID_ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

internal fun <T> flatMapValidCardIdFlow(
    cardIdFlow: Flow<String?>,
    multiverseIdFlow: Flow<Int>,
    flatMapById: (cardId: String) -> Flow<T?>,
    flatMapByMultiverseId: (multiverseId: Int) -> Flow<T?>,
): Flow<T> =
    combine(cardIdFlow, multiverseIdFlow) { cardId, multiverseId -> cardId to multiverseId }
        .flatMapLatest { (currentCardId, currentMultiverseId) ->
            when {
                currentCardId != null -> flatMapById(currentCardId)
                currentMultiverseId != INVALID_ID -> flatMapByMultiverseId(currentMultiverseId)
                else -> flowOf(null)
            }
        }
        .filterNotNull()
