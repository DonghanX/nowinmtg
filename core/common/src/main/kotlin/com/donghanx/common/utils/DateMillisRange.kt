package com.donghanx.common.utils

data class DateMillisRange(val startMillis: Long? = null, val endMillis: Long? = null) {

    companion object {
        val EMPTY = DateMillisRange()
    }

    fun isEmpty(): Boolean = startMillis == null && endMillis == null

    fun isNotEmpty(): Boolean = !isEmpty()

    fun hasStartMillisOnly(): Boolean = startMillis != null && endMillis == null

    fun hasEndMillisOnly(): Boolean = startMillis == null && endMillis != null

    fun contains(targetMillis: Long): Boolean {
        val isAfterStartMillis = startMillis?.let { targetMillis > it } ?: true
        val isBeforeEndMillis = endMillis?.let { targetMillis < it } ?: true

        return isAfterStartMillis && isBeforeEndMillis
    }
}
