package com.google.samples.apps.nowinandroid.feature.news.impl

import NewsUiState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.samples.apps.nowinandroid.core.data.repository.MyNewsRepository
import com.google.samples.apps.nowinandroid.core.network.model.onError
import com.google.samples.apps.nowinandroid.core.network.model.onLoadingChange
import com.google.samples.apps.nowinandroid.core.network.model.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class NewsViewModel @Inject constructor(
    private val repository: MyNewsRepository,
) : ViewModel() {

    private val refreshing = MutableStateFlow(false)
    private val error = MutableStateFlow<String?>(null)

    init {
        refresh()
    }

    val uiState: StateFlow<NewsUiState> =
        combine(
            repository.getNews(),
            refreshing,
            error,
        ) { news, isRefreshing, error ->
            NewsUiState(
                news = news,
                isRefreshing = isRefreshing,
                error = error,
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            NewsUiState(),
        )

    fun refresh() {
        if (refreshing.value) return

        repository
            .refresh()
            .onLoadingChange { refreshing.value = it }
            .onError {  error.value = it.message ?: ""}
            .onSuccess {  }
            .launchIn(viewModelScope)
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
}