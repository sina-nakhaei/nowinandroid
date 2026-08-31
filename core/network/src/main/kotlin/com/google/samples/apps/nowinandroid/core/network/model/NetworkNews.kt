package com.google.samples.apps.nowinandroid.core.network.model

import com.google.samples.apps.nowinandroid.core.model.data.News
import com.google.samples.apps.nowinandroid.core.model.data.NewsCategory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkNews(
    @SerialName("link")
    val link: String,

    @SerialName("og")
    val imageUrl: String? = null,

    @SerialName("source")
    val source: String,

    @SerialName("source_icon")
    val sourceIconUrl: String? = null,

    @SerialName("title")
    val title: String,
)

@Serializable
data class NetworkNewsFeed(
    @SerialName("Business")
    val business: List<NetworkNews> = emptyList(),

    @SerialName("Entertainment")
    val entertainment: List<NetworkNews> = emptyList(),

    @SerialName("Health")
    val health: List<NetworkNews> = emptyList(),

    @SerialName("Science")
    val science: List<NetworkNews> = emptyList(),

    @SerialName("Sports")
    val sports: List<NetworkNews> = emptyList(),

    @SerialName("Technology")
    val technology: List<NetworkNews> = emptyList(),

    @SerialName("US")
    val us: List<NetworkNews> = emptyList(),

    @SerialName("World")
    val world: List<NetworkNews> = emptyList(),
)

fun NetworkNews.asExternalModel(category: NewsCategory): News =
    News(
        id = link,
        title = title,
        link = link,
        imageUrl = imageUrl,
        source = source,
        sourceIconUrl = sourceIconUrl,
        category = category,
    )

fun NetworkNewsFeed.asExternalModels(): List<News> =
    buildList {
        business.forEach { add(it.asExternalModel(NewsCategory.BUSINESS)) }
        entertainment.forEach { add(it.asExternalModel(NewsCategory.ENTERTAINMENT)) }
        health.forEach { add(it.asExternalModel(NewsCategory.HEALTH)) }
        science.forEach { add(it.asExternalModel(NewsCategory.SCIENCE)) }
        sports.forEach { add(it.asExternalModel(NewsCategory.SPORTS)) }
        technology.forEach { add(it.asExternalModel(NewsCategory.TECHNOLOGY)) }
        us.forEach { add(it.asExternalModel(NewsCategory.US)) }
        world.forEach { add(it.asExternalModel(NewsCategory.WORLD)) }
    }