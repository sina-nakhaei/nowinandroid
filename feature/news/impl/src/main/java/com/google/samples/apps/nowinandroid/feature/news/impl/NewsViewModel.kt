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

import NewsError
import NewsUiState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.samples.apps.nowinandroid.core.data.repository.MyNewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class NewsViewModel @Inject constructor(
    private val repository: MyNewsRepository,
) : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    private val _hasRefreshError = MutableStateFlow(false)
    private val _refreshError = MutableStateFlow<NewsError?>(null)

    init {
        refresh()
    }

    val uiState: StateFlow<NewsUiState> =
        combine(
            repository.getNews(),
            _isRefreshing,
            _refreshError,
        ) { news, isRefreshing, error ->
            NewsUiState(
                news = news,
                isLoading = false,
                isRefreshing = isRefreshing,
                error = error,
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            NewsUiState(),
        )
    fun refresh() {
        if (uiState.value.isRefreshing) return

        viewModelScope.launch {
            _isRefreshing.value = true
            _hasRefreshError.value = false

            try {
                repository.refresh()
            } catch (e: Exception) {
                _hasRefreshError.value = true
            } finally {
                _hasRefreshError.value = false
            }
        }
    }
}