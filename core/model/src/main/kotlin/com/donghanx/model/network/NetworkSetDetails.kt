package com.donghanx.model.network
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class NetworkSetDetails(
    @SerialName("data")
    val cards: List<NetworkCardDetails>,
    @SerialName("has_more")
    val hasMore: Boolean,
    @SerialName("next_page")
    val nextPage: String?,
    @SerialName("total_cards")
    val totalCards: Int
)