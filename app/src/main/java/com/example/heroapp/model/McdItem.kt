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
    val imageUrl: String? = null,
    val price: String? = null
)

data class NutrientFact(
    val name: String?,
    val value: String?,
    val unit: String?,
    val hundred_g_per_product: String?,
    val recommended_daily_value: String?
)
