package com.donghanx.common.ext

fun String.capitalize(): String = replaceFirstChar { it.uppercase() }
