package com.google.samples.apps.nowinandroid.core.data.repository

import com.google.samples.apps.nowinandroid.core.database.dao.NewsDao
import com.google.samples.apps.nowinandroid.core.database.model.NewsEntity
import com.google.samples.apps.nowinandroid.core.database.model.asEntity
import com.google.samples.apps.nowinandroid.core.database.model.asExternalModel
import com.google.samples.apps.nowinandroid.core.model.data.News
import com.google.samples.apps.nowinandroid.core.network.NewsNetworkDataSource
import com.google.samples.apps.nowinandroid.core.network.model.Result
import com.google.samples.apps.nowinandroid.core.network.model.asExternalModels
import com.google.samples.apps.nowinandroid.core.network.model.doOnEachState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class OfflineFirstMyNewsRepository @Inject constructor(
    private val newsDao: NewsDao,
    private val network: NewsNetworkDataSource,
) : MyNewsRepository {

    override fun getNews(): Flow<List<News>> =
        newsDao.getNewsEntities()
            .map { it.map(NewsEntity::asExternalModel) }
            .flowOn(Dispatchers.IO)

    override fun getNews(id: String): Flow<News?> =
        newsDao.getNewsEntity(id)
            .map { it?.asExternalModel() }
            .flowOn(Dispatchers.IO)

    override fun refresh(): Flow<Result<Unit>> = flow {
        emit(Result.Loading)
        network
            .getNewsFeed()
            .doOnEachState(
                onSuccess = { result ->
                    emit(Result.Success(null))
                    result.data?.let { data ->
                        newsDao.replaceNews(
                            data
                                .asExternalModels()
                                .map(News::asEntity),
                            )
                    }
                },
                onError = {
                    emit(it)
                },
            )
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            newsDao.deleteAll()
        }
    }
}

