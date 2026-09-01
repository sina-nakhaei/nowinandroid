package com.google.samples.apps.nowinandroid.feature.news.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.google.samples.apps.nowinandroid.core.navigation.Navigator
import com.google.samples.apps.nowinandroid.feature.news.api.NewsDetailNavKey
import com.google.samples.apps.nowinandroid.feature.news.api.NewsNavKey
import com.google.samples.apps.nowinandroid.feature.news.impl.NewsDetailScreen
import com.google.samples.apps.nowinandroid.feature.news.impl.NewsScreen

fun EntryProviderScope<NavKey>.newsEntry(navigator: Navigator) {
    entry<NewsNavKey> {
        NewsScreen(
            onNewsClick = { newsId -> },
        )
    }

    entry<NewsDetailNavKey> { key ->
        NewsDetailScreen()
    }
}