package com.google.samples.apps.nowinandroid.core.data.repository

import com.google.samples.apps.nowinandroid.core.database.dao.NewsDao
import com.google.samples.apps.nowinandroid.core.database.model.NewsEntity
import com.google.samples.apps.nowinandroid.core.database.model.asEntity
import com.google.samples.apps.nowinandroid.core.database.model.asExternalModel
import com.google.samples.apps.nowinandroid.core.model.data.News
import com.google.samples.apps.nowinandroid.core.network.NewsNetworkDataSource
import com.google.samples.apps.nowinandroid.core.network.model.asExternalModels
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class OfflineFirstMyNewsRepository @Inject constructor(
    private val newsDao: NewsDao,
    private val network: NewsNetworkDataSource,
) : MyNewsRepository {

    override fun getNews(): Flow<List<News>> =
        newsDao.getNewsEntities()
            .map { it.map(NewsEntity::asExternalModel) }

    override fun getNews(id: String): Flow<News?> =
        newsDao.getNewsEntity(id)
            .map { it?.asExternalModel() }

    override suspend fun refresh() {
        val news = network.getNewsFeed()
            .asExternalModels()
            .map(News::asEntity)

        newsDao.replaceNews(news)
    }
}

