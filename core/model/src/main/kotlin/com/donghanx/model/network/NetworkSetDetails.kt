package com.donghanx.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkSetDetails(
    @SerialName("data") val cards: List<NetworkCardDetails>,
    @SerialName("has_more") val hasMore: Boolean,
    @SerialName("next_page") val nextPage: String?,
    @SerialName("total_cards") val totalCards: Int,
)
