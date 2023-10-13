package com.donghanx.data.repository.cards

import com.donghanx.common.NetworkResult
import com.donghanx.common.asResultFlow
import com.donghanx.common.foldResult
import com.donghanx.database.RandomCardsDao
import com.donghanx.database.model.RandomCardEntity
import com.donghanx.database.model.asExternalModel
import com.donghanx.database.model.asRandomCardEntity
import com.donghanx.model.Card
import com.donghanx.network.di.MtgCardsRemoteDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class RandomCardsRepository
@Inject
constructor(
    private val randomCardsDao: RandomCardsDao,
    private val cardsRemoteDataSource: MtgCardsRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : CardsRepository {
    override fun getRandomCards(): Flow<List<Card>> =
        randomCardsDao
            .getRandomCards()
            .map { it.map(RandomCardEntity::asExternalModel) }
            .flowOn(ioDispatcher)

    override fun refreshRandomCards(shouldContainImageUrl: Boolean): Flow<NetworkResult<Unit>> =
        flow { emit(cardsRemoteDataSource.getRandomCards()) }
            .asResultFlow()
            .foldResult(
                onSuccess = { randomCards ->
                    randomCards
                        .filterNot { shouldContainImageUrl && it.imageUrl == null }
                        .map { it.asRandomCardEntity() }
                        .also { randomCardsDao.deleteAllAndInsertRandomCards(it) }

                    NetworkResult.Success(Unit)
                },
                onError = { NetworkResult.Error(it) }
            )
}
