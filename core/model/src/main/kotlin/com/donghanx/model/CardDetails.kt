package com.donghanx.model

import com.donghanx.model.network.Ruling

data class CardDetails(
    val multiverseId: Int,
    val artist: String?,
    val cmc: Double,
    val colorIdentity: List<String>?,
    val colors: List<String>?,
    val flavor: String?,
    val imageUrl: String?,
    val layout: String,
    val manaCost: String?,
    val name: String,
    val number: String,
    val originalText: String?,
    val originalType: String?,
    val power: String?,
    val printings: List<String>,
    val rarity: String,
    val set: String,
    val setName: String,
    val subtypes: List<String>?,
    val supertypes: List<String>?,
    val text: String?,
    val toughness: String?,
    val type: String,
    val types: List<String>?,
    val variations: List<String>?,
    val rulings: List<Ruling>?,
)
