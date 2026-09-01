/*
 * Copyright 2026 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.nowinandroid.feature.news.impl

import NewsUiState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.samples.apps.nowinandroid.core.model.data.News
import com.google.samples.apps.nowinandroid.core.ui.util.shimmer

@Composable
internal fun NewsScreen(
    onNewsClick: (String) -> Unit,
    onShowSnackbar: suspend (String) -> Boolean,
    modifier: Modifier = Modifier,
) {
    val viewModel: NewsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            val snackbarResult = onShowSnackbar(it)

            if (snackbarResult) {
                viewModel.dismissError()
            }
        }
    }
    NewsContent(
        uiState = uiState,
        onNewsClick = onNewsClick,
        onRefresh = viewModel::refresh,
        onDeleteAll = viewModel::deleteAll,
        modifier = modifier,
    )
}

@Composable
private fun NewsContent(
    uiState: NewsUiState,
    onNewsClick: (String) -> Unit,
    onRefresh: () -> Unit,
    onDeleteAll: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PullToRefreshBox(
        isRefreshing = uiState.isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier.fillMaxSize(),
    ) {
        when {
            uiState.news.isEmpty() -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    item {

                        if (uiState.isRefreshing) {
                            SheetLoading()
                        } else {
                            Text(
                                textAlign = TextAlign.Center,
                                text = if (uiState.hasRefreshError) {
                                    "Something went wrong\n Pull to Refresh news"
                                } else {
                                    "No news\n Pull to Refresh news"
                                },
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(
                        items = uiState.news,
                        key = { it.id },
                    ) { news ->
                        NewsItem(
                            news = news,
                            onClick = { onNewsClick(news.id) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NewsItem(
    news: News,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        Text(
            text = news.title,
            fontWeight = FontWeight.ExtraBold,
        )

        Text(
            text = news.source,
            color = Color(0xFFA8A8A8),
        )
    }
}

@Composable
private fun SheetLoading() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 24.dp,
            alignment = Alignment.CenterVertically,
        ),
    ) {
        repeat(20) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(32.dp))
                    .shimmer()
                    .fillMaxWidth()
                    .padding(52.dp),
            ) { }
        }
    }
}