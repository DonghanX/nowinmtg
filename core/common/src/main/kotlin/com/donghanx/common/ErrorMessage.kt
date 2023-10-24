package com.donghanx.common

data class ErrorMessage(val id: Int, val message: String, val hasError: Boolean = true) {
    operator fun invoke(): String = message
}

fun emptyErrorMessage(): ErrorMessage = ErrorMessage(id = 0, message = "", hasError = false)

fun Throwable?.asErrorMessage(id: Int): ErrorMessage =
    ErrorMessage(id = id, message = this?.message.orEmpty(), hasError = true)
