package com.google.samples.apps.nowinandroid.feature.news.impl.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult.Dismissed
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.google.samples.apps.nowinandroid.core.navigation.Navigator
import com.google.samples.apps.nowinandroid.core.ui.util.LocalSnackbarHostState
import com.google.samples.apps.nowinandroid.feature.news.api.NewsDetailNavKey
import com.google.samples.apps.nowinandroid.feature.news.api.NewsNavKey
import com.google.samples.apps.nowinandroid.feature.news.impl.NewsDetailScreen
import com.google.samples.apps.nowinandroid.feature.news.impl.NewsScreen

fun EntryProviderScope<NavKey>.newsEntry(navigator: Navigator) {
    entry<NewsNavKey> {
        val snackbarHostState = LocalSnackbarHostState.current
        NewsScreen(
            onNewsClick = { newsId ->
                navigator.navigate(NewsDetailNavKey(newsId))
            },
            onShowSnackbar = { message ->
                snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = null,
                    duration = SnackbarDuration.Short,
                ) == Dismissed
            },
        )
    }

    entry<NewsDetailNavKey> { key ->
        NewsDetailScreen(key.newsId)
    }
}