package com.donghanx.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkSets(
    @SerialName("data") val data: List<NetworkSet>,
)

@Serializable
data class NetworkSet(
    @SerialName("code") val code: String,
    @SerialName("card_count") val cardCount: Int,
    @SerialName("digital") val digital: Boolean,
    @SerialName("icon_svg_uri") val iconSvgUri: String,
    @SerialName("id") val scryfallId: String,
    @SerialName("name") val name: String,
    @SerialName("set_type") val setType: String,
    @SerialName("released_at") val releasedAt: String,
    @SerialName("scryfall_uri") val scryfallUri: String,
    @SerialName("search_uri") val searchUri: String,
    @SerialName("uri") val uri: String
)
