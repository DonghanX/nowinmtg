package com.donghanx.common.extensions

fun String.capitalize(): String = replaceFirstChar { it.uppercase() }
