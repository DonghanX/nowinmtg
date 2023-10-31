package com.donghanx.data.repository.sets

import com.donghanx.model.NetworkSet

interface SetsRepository {

    suspend fun getAllSets(): List<NetworkSet>
}