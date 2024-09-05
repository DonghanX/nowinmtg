package com.donghanx.domain

import com.donghanx.data.repository.carddetails.CardDetailsRepository
import com.donghanx.model.CardDetails
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class ObserveCardDetailsUseCase
@Inject
constructor(private val cardDetailsRepository: CardDetailsRepository) {

    operator fun invoke(
        cardIdFlow: StateFlow<String?>,
        multiverseIdFlow: StateFlow<Int>,
    ): Flow<CardDetails?> =
        flatMapValidCardIdFlow(
            cardIdFlow = cardIdFlow,
            multiverseIdFlow = multiverseIdFlow,
            flatMapById = cardDetailsRepository::getCardDetailsById,
            flatMapByMultiverseId = cardDetailsRepository::getCardDetailsByMultiverseId,
        )
}
