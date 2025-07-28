package com.donghanx.domain

import com.donghanx.common.ErrorMessage
import com.donghanx.common.NetworkResult
import com.donghanx.common.asErrorMessage
import com.donghanx.common.emptyErrorMessage
import com.donghanx.data.repository.setdetails.SetDetailsRepository
import com.donghanx.data.repository.sets.SetsRepository
import com.donghanx.model.CardPreview
import com.donghanx.model.SetInfo
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

class ObserveSetDetailsUseCase
@Inject
constructor(
    private val setsRepository: SetsRepository,
    private val setDetailsRepository: SetDetailsRepository,
) {
    operator fun invoke(setId: String): Flow<SetDetailsUseCaseResult> {
        val setInfoFlow = setsRepository.getSetInfoById(setId)

        val localUiStateFlow =
            setInfoFlow
                .filterNotNull()
                .flatMapLatest { setInfo ->
                    setDetailsRepository.getCardsInCurrentSet(setInfo.code).map { cards ->
                        SetDetailsUseCaseResult(setInfo = setInfo, cards = cards)
                    }
                }
                .filter { it.cards.isNotEmpty() }

        val refreshErrorFlow =
            setInfoFlow
                .filterNotNull()
                .flatMapLatest { setInfo ->
                    setDetailsRepository.refreshCardsInCurrentSet(setInfo.searchUri)
                }
                .filterIsInstance<NetworkResult.Error>()
                .map { SetDetailsUseCaseResult(errorMessage = it.exception.asErrorMessage()) }

        return merge(localUiStateFlow, refreshErrorFlow)
    }
}

data class SetDetailsUseCaseResult(
    val setInfo: SetInfo? = null,
    val cards: List<CardPreview> = listOf(),
    val errorMessage: ErrorMessage = emptyErrorMessage(),
)
