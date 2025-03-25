package com.example.heroapp.model

data class McdMenuResponse(
    val categories: List<McdCategory>
)

data class McdCategory(
    val name: String,
    val products: List<Int>
)
