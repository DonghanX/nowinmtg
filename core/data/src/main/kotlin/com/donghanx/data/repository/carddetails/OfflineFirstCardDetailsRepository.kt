package com.donghanx.data.repository.carddetails

import com.donghanx.common.NetworkResult
import com.donghanx.common.asResultFlow
import com.donghanx.common.foldResult
import com.donghanx.data.sync.syncWith
import com.donghanx.database.CardDetailsDao
import com.donghanx.database.RulingsDao
import com.donghanx.database.model.RulingsEntity
import com.donghanx.database.model.asCardDetailsEntity
import com.donghanx.database.model.asExternalModel
import com.donghanx.database.model.asRulingsEntity
import com.donghanx.model.CardDetails
import com.donghanx.model.Ruling
import com.donghanx.model.network.NetworkCardDetails
import com.donghanx.network.CardsRemoteDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class OfflineFirstCardDetailsRepository
@Inject
constructor(
    private val cardDetailsDao: CardDetailsDao,
    private val rulingsDao: RulingsDao,
    private val cardsRemoteDataSource: CardsRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : CardDetailsRepository {

    override fun getCardDetailsById(cardId: String): Flow<CardDetails?> =
        cardDetailsDao.getCardDetailsById(cardId).map { it?.asExternalModel() }.flowOn(ioDispatcher)

    override fun getCardDetailsByMultiverseId(multiverseId: Int): Flow<CardDetails?> =
        cardDetailsDao
            .getCardDetailsByMultiverseId(multiverseId)
            .map { it?.asExternalModel() }
            .flowOn(ioDispatcher)

    override fun refreshCardDetailsById(cardId: String): Flow<NetworkResult<Unit>> =
        flow { emit(cardsRemoteDataSource.getCardDetailsByCardId(cardId)) }
            .reflectCardDetailsChanges()
            .flowOn(ioDispatcher)

    override fun refreshCardDetailsByMultiverseId(multiverseId: Int): Flow<NetworkResult<Unit>> =
        flow { emit(cardsRemoteDataSource.getCardDetailsByMultiverseId(multiverseId)) }
            .reflectCardDetailsChanges()
            .flowOn(ioDispatcher)

    override suspend fun oneshotGetCardRulingsById(cardId: String): List<Ruling> =
        rulingsDao.getRulingsByCardId(cardId)?.let(RulingsEntity::asExternalModel).orEmpty()

    override fun refreshCardRulingsById(cardId: String): Flow<NetworkResult<Unit>> =
        flow { emit(cardsRemoteDataSource.getCardRulingsById(cardId)) }
            .asResultFlow()
            .foldResult(
                onSuccess = { rulings ->
                    rulings
                        .takeIf { it.isNotEmpty() }
                        ?.syncWith(
                            entityConverter = { it.asRulingsEntity(cardId) },
                            modelActions = rulingsDao::upsertRuling,
                        )
                    NetworkResult.Success(Unit)
                },
                onError = { NetworkResult.Error(it) },
            )
            .flowOn(ioDispatcher)

    private fun Flow<NetworkCardDetails>.reflectCardDetailsChanges(): Flow<NetworkResult<Unit>> =
        asResultFlow()
            .foldResult(
                onSuccess = { cardDetails ->
                    cardDetails.syncWith(
                        entityConverter = NetworkCardDetails::asCardDetailsEntity,
                        modelActions = cardDetailsDao::upsertCardDetails,
                    )
                    NetworkResult.Success(Unit)
                },
                onError = { NetworkResult.Error(it) },
            )
}
