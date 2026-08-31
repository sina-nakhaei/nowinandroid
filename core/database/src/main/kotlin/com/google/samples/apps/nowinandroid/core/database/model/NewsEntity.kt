package com.google.samples.apps.nowinandroid.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.samples.apps.nowinandroid.core.database.util.NewsConverter
import com.google.samples.apps.nowinandroid.core.model.data.News
import com.google.samples.apps.nowinandroid.core.model.data.NewsCategory

@Entity(
    tableName = "news",
)
@TypeConverters(NewsConverter::class)
data class NewsEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val link: String,
    val imageUrl: String?,
    val source: String,
    val sourceIconUrl: String?,
    val category: NewsCategory,
)

fun NewsEntity.asExternalModel(): News =
    News(
        id = id,
        title = title,
        link = link,
        imageUrl = imageUrl,
        source = source,
        sourceIconUrl = sourceIconUrl,
        category = category,
    )

fun News.asEntity(): NewsEntity =
    NewsEntity(
        id = id,
        title = title,
        link = link,
        imageUrl = imageUrl,
        source = source,
        sourceIconUrl = sourceIconUrl,
        category = category,
    )

