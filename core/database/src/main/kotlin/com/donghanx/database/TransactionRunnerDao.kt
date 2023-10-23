package com.donghanx.database

import androidx.room.Dao
import androidx.room.Ignore
import androidx.room.Transaction

@Dao
interface TransactionRunnerDao : TransactionRunner {
    @Transaction
    suspend fun runInTransaction(block: suspend () -> Unit) {
        block()
    }

    @Ignore
    override suspend fun invoke(block: suspend () -> Unit) {
        runInTransaction(block)
    }
}

interface TransactionRunner {
    suspend operator fun invoke(block: suspend () -> Unit)
}
