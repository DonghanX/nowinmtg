package com.donghanx.model
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class NetworkSets(
    @SerialName("sets")
    val sets: List<NetworkSet>
)

@Serializable
data class NetworkSet(
    @SerialName("block")
    val block: String?,
    @SerialName("code")
    val code: String,
    @SerialName("name")
    val name: String,
    @SerialName("onlineOnly")
    val onlineOnly: Boolean,
    @SerialName("releaseDate")
    val releaseDate: String,
    @SerialName("type")
    val type: String
)