package com.donghanx.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO: Handle multi-faces card
@Serializable
data class NetworkCardDetails(
    // -- Core card fields start --
    // A unique ID for this card in Scryfall’s database.
    @SerialName("id") val id: String,
    @SerialName("oracle_id") val oracleId: String?,
    @SerialName("lang") val lang: String,
    @SerialName("mtgo_id") val mtgoId: Int?,
    @SerialName("mtgo_foil_id") val mtgoFoilId: Int?,
    @SerialName("multiverse_ids") val multiverseIds: List<Int>?,
    @SerialName("tcgplayer_id") val tcgPlayerId: Int?,
    @SerialName("cardmarket_id") val cardMarketId: Int?,
    // The layout property categorizes the arrangement of card parts, faces, and other bounded
    // regions on cards. The layout can be used to programmatically determine which other properties
    // on a card you can expect.
    @SerialName("layout") val layout: String,
    // A link to where you can begin paginating all re/prints for this card on Scryfall’s API.
    @SerialName("prints_search_uri") val printsSearchUri: String,
    @SerialName("uri") val uri: String,
    @SerialName("rulings_uri") val rulingsUri: String,
    @SerialName("scryfall_uri") val scryfallUri: String,
    // -- Core card fields end --
    // -- Gameplay fields start --
    @SerialName("name") val name: String,
    @SerialName("cmc") val cmc: Double?,
    @SerialName("color_identity") val colorIdentity: List<String>,
    @SerialName("colors") val colors: List<String>?,
    @SerialName("defense") val defense: String?,
    @SerialName("power") val power: String?,
    @SerialName("toughness") val toughness: String?,
    @SerialName("type_line") val typeLine: String?,
    @SerialName("reserved") val reserved: Boolean,
    @SerialName("mana_cost") val manaCost: String?,
    @SerialName("edhrec_rank") val edhrecRank: Int?,
    @SerialName("keywords") val keywords: List<String>,
    @SerialName("legalities") val legalities: Legalities?,
    @SerialName("loyalty") val loyalty: String?,
    @SerialName("oracle_text") val oracleText: String?,
    @SerialName("penny_rank") val pennyRank: Int?,
    @SerialName("produced_mana") val producedMana: List<String>?,
    // -- Gameplay fields end --
    // -- Print fields start
    @SerialName("artist") val artist: String?,
    @SerialName("artist_ids") val artistIds: List<String>?,
    @SerialName("booster") val booster: Boolean,
    @SerialName("border_color") val borderColor: String,
    @SerialName("card_back_id") val cardBackId: String?,
    @SerialName("collector_number") val collectorNumber: String,
    @SerialName("content_warning") val contentWarning: Boolean?,
    @SerialName("digital") val digital: Boolean,
    @SerialName("finishes") val finishes: List<String>,
    @SerialName("flavor_text") val flavorText: String?,
    @SerialName("flavor_name") val flavorName: String?,
    @SerialName("frame") val frame: String,
    @SerialName("full_art") val fullArt: Boolean,
    @SerialName("games") val games: List<String>,
    @SerialName("highres_image") val highResImage: Boolean,
    @SerialName("illustration_id") val illustrationId: String?,
    @SerialName("image_status") val imageStatus: String,
    @SerialName("image_uris") val imageUris: ImageUris?,
    @SerialName("oversized") val oversized: Boolean,
    @SerialName("prices") val prices: Prices,
    @SerialName("printed_name") val printedName: String?,
    @SerialName("printed_text") val printedText: String?,
    @SerialName("printed_type_line") val printedTypeLine: String?,
    @SerialName("promo") val promo: Boolean,
    @SerialName("promo_types") val promoTypes: List<String>?,
    @SerialName("purchase_uris") val purchaseUris: PurchaseUris?,
    @SerialName("rarity") val rarity: String,
    @SerialName("related_uris") val relatedUris: RelatedUris,
    @SerialName("released_at") val releasedAt: String,
    @SerialName("reprint") val reprint: Boolean,
    @SerialName("scryfall_set_uri") val scryfallSetUri: String,
    @SerialName("set") val `set`: String,
    @SerialName("set_id") val setId: String,
    @SerialName("set_name") val setName: String,
    @SerialName("set_search_uri") val setSearchUri: String,
    @SerialName("set_type") val setType: String,
    @SerialName("set_uri") val setUri: String,
    @SerialName("story_spotlight") val storySpotlight: Boolean,
    @SerialName("textless") val textless: Boolean,
    @SerialName("variation") val variation: Boolean,
    // -- Print fields end
    @SerialName("foil") val foil: Boolean?,
    @SerialName("nonfoil") val nonFoil: Boolean?,
)

@Serializable
data class ImageUris(
    @SerialName("art_crop") val artCrop: String,
    @SerialName("border_crop") val borderCrop: String,
    @SerialName("large") val large: String,
    @SerialName("normal") val normal: String,
    @SerialName("png") val png: String,
    @SerialName("small") val small: String,
)

@Serializable
data class Legalities(
    @SerialName("alchemy") val alchemy: String?,
    @SerialName("brawl") val brawl: String?,
    @SerialName("commander") val commander: String?,
    @SerialName("duel") val duel: String?,
    @SerialName("explorer") val explorer: String?,
    @SerialName("future") val future: String?,
    @SerialName("gladiator") val gladiator: String?,
    @SerialName("historic") val historic: String?,
    @SerialName("legacy") val legacy: String?,
    @SerialName("modern") val modern: String?,
    @SerialName("oathbreaker") val oathBreaker: String?,
    @SerialName("oldschool") val oldSchool: String?,
    @SerialName("pauper") val pauper: String?,
    @SerialName("paupercommander") val pauperCommander: String?,
    @SerialName("penny") val penny: String?,
    @SerialName("pioneer") val pioneer: String?,
    @SerialName("predh") val preDh: String?,
    @SerialName("premodern") val preModern: String?,
    @SerialName("standard") val standard: String?,
    @SerialName("standardbrawl") val standardBrawl: String?,
    @SerialName("timeless") val timeless: String?,
    @SerialName("vintage") val vintage: String?,
)

@Serializable
data class Prices(
    @SerialName("eur") val eur: String?,
    @SerialName("eur_foil") val eurFoil: String?,
    @SerialName("tix") val tix: String?,
    @SerialName("usd") val usd: String?,
    @SerialName("usd_etched") val usdEtched: String?,
    @SerialName("usd_foil") val usdFoil: String?,
)

@Serializable
data class PurchaseUris(
    @SerialName("cardhoarder") val cardHoarder: String?,
    @SerialName("cardmarket") val cardMarket: String?,
    @SerialName("tcgplayer") val tcgPlayer: String?,
)

@Serializable
data class RelatedUris(
    @SerialName("edhrec") val edhrec: String?,
    @SerialName("gatherer") val gatherer: String?,
    @SerialName("tcgplayer_infinite_articles") val tcgplayerInfiniteArticles: String?,
    @SerialName("tcgplayer_infinite_decks") val tcgplayerInfiniteDecks: String?,
)
