package com.donghanx.data.repository.sets

import com.donghanx.common.NetworkResult
import com.donghanx.common.asResultFlow
import com.donghanx.common.foldResult
import com.donghanx.data.sync.syncListWith
import com.donghanx.database.SetsDao
import com.donghanx.database.model.SetEntity
import com.donghanx.database.model.asExternalModel
import com.donghanx.database.model.asSetEntity
import com.donghanx.model.SetInfo
import com.donghanx.model.network.NetworkSet
import com.donghanx.network.SetsRemoteDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class OfflineFirstSetsRepository
@Inject
constructor(
    private val setsDao: SetsDao,
    private val setsRemoteDataSource: SetsRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : SetsRepository {
    override fun getAllSets(): Flow<List<SetInfo>> =
        setsDao.getAllSets().map { it.map(SetEntity::asExternalModel) }.flowOn(ioDispatcher)

    override fun refreshAllSets(): Flow<NetworkResult<Unit>> =
        flow { emit(setsRemoteDataSource.getAllSets()) }
            .asResultFlow()
            .foldResult(
                onSuccess = { sets ->
                    sets.syncListWith(
                        entityConverter = NetworkSet::asSetEntity,
                        modelActions = setsDao::upsertSets,
                    )
                    NetworkResult.Success(Unit)
                },
                onError = { NetworkResult.Error(it) },
            )
            .flowOn(ioDispatcher)

    override fun searchAllSetsByQuery(query: String): Flow<List<SetInfo>> =
        setsDao
            .searchAllSetsByQuery(query)
            .distinctUntilChanged()
            .map { it.map(SetEntity::asExternalModel) }
            .flowOn(ioDispatcher)

    override fun getSetInfoById(id: String): Flow<SetInfo> =
        setsDao.getSetById(id).map(SetEntity::asExternalModel).flowOn(ioDispatcher)

    override suspend fun shouldFetchInitialSets(): Boolean {
        return withContext(ioDispatcher) { setsDao.getSetsCount() == 0 }
    }
}
