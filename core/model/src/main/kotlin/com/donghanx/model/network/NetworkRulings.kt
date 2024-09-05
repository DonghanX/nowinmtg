package com.donghanx.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkRulings(
    @SerialName("data") val rulings: List<NetworkRuling>?,
    @SerialName("has_more") val hasMore: Boolean,
)

@Serializable
data class NetworkRuling(
    @SerialName("comment") val comment: String,
    @SerialName("oracle_id") val oracleId: String,
    @SerialName("published_at") val publishedAt: String,
    @SerialName("source") val source: String,
)
