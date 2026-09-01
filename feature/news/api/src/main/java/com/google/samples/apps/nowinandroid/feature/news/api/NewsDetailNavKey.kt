package com.google.samples.apps.nowinandroid.feature.news.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class NewsDetailNavKey(
    val newsId: String,
) : NavKey

