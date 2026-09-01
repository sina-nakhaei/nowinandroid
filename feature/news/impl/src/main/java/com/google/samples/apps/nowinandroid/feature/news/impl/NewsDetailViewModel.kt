package com.google.samples.apps.nowinandroid.feature.news.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.samples.apps.nowinandroid.core.data.repository.MyNewsRepository
import com.google.samples.apps.nowinandroid.core.model.data.News
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = NewsDetailViewModel.Factory::class)
internal class NewsDetailViewModel @AssistedInject constructor(
    private val repository: MyNewsRepository,
    @Assisted private val newsId: String,
) : ViewModel() {
    val news: StateFlow<News?> =
        repository.getNews(newsId)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                null,
            )

    @AssistedFactory
    interface Factory {
        fun create(newsId: String): NewsDetailViewModel
    }
}

