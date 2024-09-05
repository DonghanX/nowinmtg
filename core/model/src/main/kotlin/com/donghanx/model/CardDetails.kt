package com.donghanx.model

import com.donghanx.model.network.ImageUris

data class CardDetails(
    val id: String,
    val multiverseId: Int?,
    val artist: String?,
    val cmc: Double,
    val colorIdentity: List<String>?,
    val colors: List<String>?,
    val flavor: String?,
    val imageUris: ImageUris?,
    val layout: String,
    val manaCost: String?,
    val name: String,
    val text: String?,
    val typeLine: String,
    val power: String?,
    val rarity: String,
    val set: String,
    val setName: String,
    val toughness: String?,
)
