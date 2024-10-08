package com.donghanx.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable data class NetworkCards(@SerialName("cards") val cards: List<NetworkCard>)

@Serializable
data class NetworkCard(
    @SerialName("multiverseid") val multiverseId: Int,
    @SerialName("name") val name: String,
    @SerialName("imageUrl") val imageUrl: String,
    @SerialName("text") val text: String?,
    @SerialName("Types") val types: List<String>?,
)
