package com.donghanx.common.extensions

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun String.capitalize(): String = replaceFirstChar { it.uppercase() }

fun String.encodeUrl(): String = URLEncoder.encode(this, StandardCharsets.UTF_8.toString())
