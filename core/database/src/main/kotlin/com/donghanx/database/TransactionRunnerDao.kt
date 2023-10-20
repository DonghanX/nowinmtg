package com.donghanx.database

import androidx.room.Dao
import androidx.room.Ignore
import androidx.room.Transaction

@Dao
abstract class TransactionRunnerDao : TransactionRunner {
    @Transaction
    protected open suspend fun runInTransaction(block: suspend () -> Unit) {
        block()
    }

    @Ignore
    override suspend fun invoke(block: suspend () -> Unit) {
        runInTransaction(block)
    }
}

interface TransactionRunner {
    suspend operator fun invoke(tx: suspend () -> Unit)
}
