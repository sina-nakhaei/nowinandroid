package com.google.samples.apps.nowinandroid.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.google.samples.apps.nowinandroid.core.database.model.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getNewsEntities(): Flow<List<NewsEntity>>

    @Query("SELECT * FROM news WHERE id = :id")
    fun getNewsEntity(id: String): Flow<NewsEntity?>

    @Upsert
    suspend fun upsertNews(entities: List<NewsEntity>)

    @Query("DELETE FROM news")
    suspend fun deleteAll()

    @Transaction
    suspend fun replaceNews(entities: List<NewsEntity>) {
        deleteAll()
        upsertNews(entities)
    }
}