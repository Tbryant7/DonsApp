package com.example.heroapp.model

data class McdItem(
    val id: Int?,
    val name: String?,
    val description: String?,
    val marketing_name: String?,
    val short_name: String?,
    val menu_item_no: String?,
    val type: String?,
    val keywords: List<String>?,
    val nutrient_facts: List<NutrientFact>?,

    // Manually injected fields for UI
    val calories: Int? = null,
    val apiImageUrl: String? = null,
    val price: String? = null
){

    // BEC Biscut - 200300
    // Egg Mcmuffin - 200298
    // Sasuage McMuffin - 200449
    // Sausage McMuffin with Egg - 200161
    // Sausage Biscuit - 200301
    // Sausage Biscuit with Egg - 200302
    // BEC McGriddles - 200304
    // Sausage McGriddles - 200306
    // SEC McGriddles - 200307
    // Big Breakfast - 200322

    val imageUrl: String
        get() = apiImageUrl ?: companionImages[id] ?: "https://via.placeholder.com/300x180.png?text=No+Image"

    companion object {
        private val companionImages = mapOf(
            200300 to "https://s7d1.scene7.com/is/image/mcdonalds/DC_202405_0085_BaconEggCheeseBiscuit_1564x1564-1:nutrition-calculator-tile",
            200298 to "https://s7d1.scene7.com/is/image/mcdonalds/DC_202004_0046_EggMcMuffin_1564x1564-1:nutrition-calculator-tile",
            200449 to "https://s7d1.scene7.com/is/image/mcdonalds/DC_202411_0078_SausageMcMuffin_McValue_1564x1564:nutrition-calculator-tile",
            200161 to "https://s7d1.scene7.com/is/image/mcdonalds/DC_201907_0083_SausageEggMcMuffin_1564x1564-1:nutrition-calculator-tile",
            200301 to "https://s7d1.scene7.com/is/image/mcdonalds/DC_202411_0062_SausageBiscuit_McValue_1564x1564:nutrition-calculator-tile",
            200302 to "https://s7d1.scene7.com/is/image/mcdonalds/DC_201907_0092_SausageEggBiscuit_1564x1564-1:nutrition-calculator-tile",
            200304 to "https://s7d1.scene7.com/is/image/mcdonalds/DC_201908_9839_BEC_McGriddle_1564x1564-1:nutrition-calculator-tile",
            200306 to "https://s7d1.scene7.com/is/image/mcdonalds/DC_201911_6110_SausageMcGriddle_1564x1564-1:nutrition-calculator-tile",
            200307 to "https://s7d1.scene7.com/is/image/mcdonalds/DC_201907_9841_SausageEggCheeseMcGriddle_1564x1564-2:nutrition-calculator-tile",
            200322 to "https://s7d1.scene7.com/is/image/mcdonalds/DC_202405_0107_BigBreakfast._1564x1564-1:nutrition-calculator-tile"
        )
        }
}

data class NutrientFact(
    val name: String?,
    val value: String?,
    val unit: String?,
    val hundred_g_per_product: String?,
    val recommended_daily_value: String?
)
