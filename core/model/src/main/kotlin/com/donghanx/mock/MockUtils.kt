package com.donghanx.mock

import com.donghanx.model.CardDetails
import com.donghanx.model.CardPreview
import com.donghanx.model.SetInfo
import com.donghanx.model.network.Ruling

object MockUtils {
    val emptyCards = List(5) { CardPreview(id = it.toString(), name = "", imageUrl = "") }

    val cardDetailsAvacyn: CardDetails =
        CardDetails(
            name = "Archangel Avacyn",
            manaCost = "{3}{W}{W}",
            cmc = 5.0,
            colors = listOf("White"),
            colorIdentity = listOf("R", "W"),
            type = "Legendary Creature — Angel",
            supertypes = listOf("Legendary"),
            types = listOf("Creature"),
            subtypes = listOf("Angel"),
            rarity = "Mythic Rare",
            set = "SOI",
            text =
                "Flash\nFlying, vigilance\nWhen Archangel Avacyn enters the battlefield, creatures you control gain indestructible until end of turn.\nWhen a non-Angel creature you control dies, transform Archangel Avacyn at the beginning of the next upkeep.",
            artist = "James Ryman",
            number = "5a",
            power = "4",
            toughness = "4",
            layout = "double-faced",
            multiverseId = "409741",
            imageUrl =
                "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=409741&type=card",
            rulings =
                listOf(
                    Ruling(
                        date = "2016-04-08",
                        text =
                            "Archangel Avacyn’s delayed triggered ability triggers at the beginning of the next upkeep regardless of whose turn it is."
                    ),
                    Ruling(
                        date = "2016-04-08",
                        text =
                            "Archangel Avacyn’s delayed triggered ability won’t cause it to transform back into Archangel Avacyn if it has already transformed into Avacyn, the Purifier, perhaps because several creatures died in one turn."
                    ),
                    Ruling(
                        date = "2016-04-08",
                        text =
                            "For more information on double-faced cards, see the Shadows over Innistrad mechanics article(http://magic.wizards.com/en/articles/archive/feature/shadows-over-innistrad-mechanics)."
                    )
                ),
            //            foreignNames =
            //                listOf(
            //                    ForeignName(
            //                        name = "大天使艾维欣 // 净罪天使艾维欣",
            //                        language = "Chinese Simplified",
            //                        text =
            //
            // "闪现\\n飞行，警戒\\n当大天使艾维欣进战场时，由你操控的生物获得不灭异能直到回合结束。\\n当一个由你操控且非天使的生物死去，在下一个维持开始时，转化大天使艾维欣。",
            //                        type = "传奇生物～天使",
            //                        faceName = "大天使艾维欣",
            //                        imageUrl =
            //
            // "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=410071&type=card",
            //                        multiverseId = "410071",
            //                        flavor = null
            //                    ),
            //                    ForeignName(
            //                        name = "Archange Avacyn // Avacyn, la purificatrice",
            //                        language = "French",
            //                        text =
            //                            "Flash\\nVol, vigilance\\nQuand l'Archange Avacyn arrive
            // sur le champ de bataille, les créatures que vous contrôlez acquièrent
            // l'indestructible jusqu'à la fin du tour.\\nQuand une créature non-Ange que vous
            // contrôlez meurt, transformez l'Archange Avacyn au début du prochain entretien.",
            //                        flavor = null,
            //                        faceName = "Archange Avacyn",
            //                        type = "Créature légendaire : ange",
            //                        imageUrl =
            //
            // "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=411061&type=card",
            //                        multiverseId = "411061"
            //                    )
            //                ),
            //            legalities = null,
            printings = listOf("SOI"),
            originalText =
                "Flash\nFlying, vigilance\nWhen Archangel Avacyn enters the battlefield, creatures you control gain indestructible until end of turn.\nWhen a non-Angel creature you control dies, transform Archangel Avacyn at the beginning of the next upkeep.",
            originalType = "Legendary Creature — Angel",
            id = "ad53597a-4448-5cbd-82bf-11d163b0c14f",
            flavor = null,
            setName = "Shadows over Innistrad",
            variations = null
        )

    val cardDetailsIncomplete: CardDetails =
        CardDetails(
            name = "",
            manaCost = null,
            cmc = 5.0,
            colors = null,
            colorIdentity = null,
            type = "Legendary Creature — Angel",
            supertypes = null,
            types = null,
            subtypes = null,
            rarity = "Mythic Rare",
            set = "SOI",
            text = null,
            artist = null,
            number = "5a",
            power = null,
            toughness = null,
            layout = "double-faced",
            multiverseId = null,
            imageUrl = null,
            printings = listOf("SOI"),
            originalText = null,
            originalType = null,
            id = "ad53597a-4448-5cbd-82bf-11d163b0c14f",
            flavor = null,
            setName = "Shadows over Innistrad",
            variations = null,
            rulings = null
        )

    val soiExpansion =
        SetInfo(
            scryfallId = "5e914d7e-c1e9-446c-a33d-d093c02b2743",
            code = "soi",
            name = "Shadows over Innistrad",
            cardCount = 297,
            digital = false,
            iconSvgUri = "https://svgs.scryfall.io/sets/soi.svg?1699246800",
            setType = "expansion",
            releasedAt = "2016-04-08",
            scryfallUri = "https://scryfall.com/sets/soi",
            searchUri =
                "https://api.scryfall.com/cards/search?include_extras=true&include_variations=true&order=set&q=e%3Asoi&unique=prints",
            uri = "https://api.scryfall.com/sets/5e914d7e-c1e9-446c-a33d-d093c02b2743"
        )

    val xlnExpansion =
        SetInfo(
            scryfallId = "fe0dad85-54bc-4151-9200-d68da84dd0f2",
            code = "xln",
            name = "Ixalan",
            cardCount = 289,
            digital = false,
            iconSvgUri = "https://svgs.scryfall.io/sets/xln.svg?1699246800",
            setType = "expansion",
            releasedAt = "2017-09-29",
            scryfallUri = "https://scryfall.com/sets/xln",
            searchUri =
                "https://api.scryfall.com/cards/search?include_extras=true&include_variations=true&order=set&q=e%3Axln&unique=prints",
            uri = "https://api.scryfall.com/sets/fe0dad85-54bc-4151-9200-d68da84dd0f2"
        )
}
