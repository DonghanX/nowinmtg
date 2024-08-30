package com.donghanx.model

data class SetInfo(
    val scryfallId: String,
    val code: String,
    val cardCount: Int,
    val digital: Boolean,
    val iconSvgUri: String,
    val name: String,
    val setType: String,
    val releasedAt: String,
    val scryfallUri: String,
    val searchUri: String,
    val uri: String,
)
