package com.google.samples.apps.nowinandroid.core.data.repository

import com.google.samples.apps.nowinandroid.core.model.data.News
import kotlinx.coroutines.flow.Flow

interface MyNewsRepository {
    fun getNews(): Flow<List<News>>

    fun getNews(id: String): Flow<News?>

    suspend fun refresh()
}