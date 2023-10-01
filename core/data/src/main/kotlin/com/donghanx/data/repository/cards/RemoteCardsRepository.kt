package com.donghanx.data.repository.cards

import com.donghanx.database.RandomCardsDao
import com.donghanx.database.model.RandomCardEntity
import com.donghanx.database.model.asExternalModel
import com.donghanx.database.model.asRandomCardEntity
import com.donghanx.model.Card
import com.donghanx.network.di.MtgCardsRemoteDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RandomCardsRepository
@Inject
constructor(
    private val randomCardsDao: RandomCardsDao,
    private val cardsRemoteDataSource: MtgCardsRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : CardsRepository {
    override fun getRandomCards(): Flow<List<Card>> =
        randomCardsDao.getRandomCards().map { it.map(RandomCardEntity::asExternalModel) }

    override suspend fun refreshRandomCards() {
        withContext(ioDispatcher) {
            cardsRemoteDataSource
                .getRandomCards()
                .map { it.asRandomCardEntity() }
                .also { randomCardsDao.upsertRandomCards(it) }
        }
    }
}
