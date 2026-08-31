package com.google.samples.apps.nowinandroid.core.network.model

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