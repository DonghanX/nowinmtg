package com.donghanx.domain

import com.donghanx.data.repository.carddetails.CardDetailsRepository
import com.donghanx.model.CardDetails
import com.donghanx.model.Ruling
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest

class ObserveCardDetailsUseCase
@Inject
constructor(private val cardDetailsRepository: CardDetailsRepository) {

    operator fun invoke(
        cardIdFlow: StateFlow<String?>,
        multiverseIdFlow: StateFlow<Int>,
    ): Flow<CardDetailsAndRulings> {
        val cardDetailsFlow =
            flatMapValidCardIdFlow(
                    cardIdFlow = cardIdFlow,
                    multiverseIdFlow = multiverseIdFlow,
                    flatMapById = cardDetailsRepository::getCardDetailsById,
                    flatMapByMultiverseId = cardDetailsRepository::getCardDetailsByMultiverseId,
                )
                .filterNotNull()

        val rulingsFlow =
            cardDetailsFlow.flatMapLatest { cardDetails ->
                cardDetailsRepository.refreshCardRulingsById(cardDetails.id).mapLatest {
                    cardDetailsRepository.oneshotGetCardRulingsById(cardDetails.id)
                }
            }

        return combine(cardDetailsFlow, rulingsFlow) { cardDetails, rulings ->
            CardDetailsAndRulings(cardDetails, rulings)
        }
    }
}

data class CardDetailsAndRulings(val cardDetails: CardDetails, val rulings: List<Ruling>)