package com.google.samples.apps.nowinandroid.core.model.data

data class News(
    val id: String,
    val title: String,
    val link: String,
    val imageUrl: String?,
    val source: String,
    val sourceIconUrl: String?,
    val category: NewsCategory,
)

enum class NewsCategory {
    BUSINESS,
    ENTERTAINMENT,
    HEALTH,
    SCIENCE,
    SPORTS,
    TECHNOLOGY,
    US,
    WORLD,
}