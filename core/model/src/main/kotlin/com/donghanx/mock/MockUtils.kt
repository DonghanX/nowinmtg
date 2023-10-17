package com.donghanx.mock

import com.donghanx.model.Card
import com.donghanx.model.ForeignName
import com.donghanx.model.NetworkCard
import com.donghanx.model.Ruling

object MockUtils {
    val emptyCards = List(5) { Card(id = it.toString(), name = "", text = "", imageUrl = "") }

    val externalCards =
        listOf(
            Card(
                id = "291e09c9-747e-5e5d-9ee8-179e1a296ae8",
                name = "Dorothea, Vengeful Victim // Dorothea's Retribution",
                text =
                    "Enchant creature\\nEnchanted creature has \\\"Whenever this creature attacks, create a 4/4 white Spirit creature token with flying that's tapped and attacking. Sacrifice that token at end of combat.\\\"\\nIf Dorothea's Retribution would be put into a graveyard from anywhere, exile it instead.",
                imageUrl = ""
            )
        )

    val networkCard: NetworkCard =
        NetworkCard(
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
                            "For more information on double-faced cards, see the Shadows over Innistrad mechanics article (http://magic.wizards.com/en/articles/archive/feature/shadows-over-innistrad-mechanics)."
                    )
                ),
            foreignNames =
                listOf(
                    ForeignName(
                        name = "大天使艾维欣 // 净罪天使艾维欣",
                        language = "Chinese Simplified",
                        text =
                            "闪现\\n飞行，警戒\\n当大天使艾维欣进战场时，由你操控的生物获得不灭异能直到回合结束。\\n当一个由你操控且非天使的生物死去，在下一个维持开始时，转化大天使艾维欣。",
                        type = "传奇生物～天使",
                        faceName = "大天使艾维欣",
                        imageUrl =
                            "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=410071&type=card",
                        multiverseId = "410071",
                        flavor = null
                    ),
                    ForeignName(
                        name = "Archange Avacyn // Avacyn, la purificatrice",
                        language = "French",
                        text =
                            "Flash\\nVol, vigilance\\nQuand l'Archange Avacyn arrive sur le champ de bataille, les créatures que vous contrôlez acquièrent l'indestructible jusqu'à la fin du tour.\\nQuand une créature non-Ange que vous contrôlez meurt, transformez l'Archange Avacyn au début du prochain entretien.",
                        flavor = null,
                        faceName = "Archange Avacyn",
                        type = "Créature légendaire : ange",
                        imageUrl =
                            "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=411061&type=card",
                        multiverseId = "411061"
                    )
                ),
            printings = listOf("SOI"),
            originalText =
                "Flash\nFlying, vigilance\nWhen Archangel Avacyn enters the battlefield, creatures you control gain indestructible until end of turn.\nWhen a non-Angel creature you control dies, transform Archangel Avacyn at the beginning of the next upkeep.",
            originalType = "Legendary Creature — Angel",
            id = "ad53597a-4448-5cbd-82bf-11d163b0c14f",
            flavor = null,
            legalities = null,
            setName = "Shadows over Innistrad",
            variations = null
        )
}
