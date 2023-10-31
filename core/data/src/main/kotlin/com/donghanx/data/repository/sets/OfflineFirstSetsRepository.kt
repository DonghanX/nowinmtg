package com.donghanx.data.repository.sets

import com.donghanx.model.NetworkSet
import com.donghanx.network.SetsRemoteDataSource
import javax.inject.Inject

class OfflineFirstSetsRepository
@Inject
constructor(private val setsRemoteDataSource: SetsRemoteDataSource) : SetsRepository {
    override suspend fun getAllSets(): List<NetworkSet> = setsRemoteDataSource.getAllSets()
}
