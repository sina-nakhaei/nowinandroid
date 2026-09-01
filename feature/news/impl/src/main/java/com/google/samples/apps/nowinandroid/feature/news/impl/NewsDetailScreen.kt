package com.google.samples.apps.nowinandroid.feature.news.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun NewsDetailScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: NewsDetailViewModel = hiltViewModel()
    val news by viewModel.news.collectAsStateWithLifecycle()

    news?.let {
    }
}