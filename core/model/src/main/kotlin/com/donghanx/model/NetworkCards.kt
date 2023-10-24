package com.donghanx.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable data class NetworkCards(@SerialName("cards") val cards: List<NetworkCard>)

@Serializable
data class NetworkCard(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("imageUrl") val imageUrl: String?,
    @SerialName("text") val text: String?,
    @SerialName("Types") val types: List<String>?
)
