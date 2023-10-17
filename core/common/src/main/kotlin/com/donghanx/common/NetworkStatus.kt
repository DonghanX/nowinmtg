package com.donghanx.common

data class NetworkStatus(
    val hasError: Boolean,
    val errorMessage: String = "",
    val replayTick: Int = 0
)
