package com.google.samples.apps.nowinandroid.core.data.repository

import com.google.samples.apps.nowinandroid.core.model.data.News
import com.google.samples.apps.nowinandroid.core.network.model.Result
import kotlinx.coroutines.flow.Flow

interface MyNewsRepository {
    fun getNews(): Flow<List<News>>

    fun getNews(id: String): Flow<News?>

    fun refresh(): Flow<Result<Unit>>

    suspend fun deleteAll()
}