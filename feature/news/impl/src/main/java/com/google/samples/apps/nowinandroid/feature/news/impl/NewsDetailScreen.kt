package com.google.samples.apps.nowinandroid.feature.news.impl

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun NewsDetailScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: NewsDetailViewModel = hiltViewModel()
    val news by viewModel.news.collectAsStateWithLifecycle()

    news?.let { item ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.headlineSmall,
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = item.source,
                style = MaterialTheme.typography.labelLarge,
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = item.link,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}