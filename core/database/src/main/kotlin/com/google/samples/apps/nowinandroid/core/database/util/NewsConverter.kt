package com.google.samples.apps.nowinandroid.core.database.util

import androidx.room.TypeConverter
import com.google.samples.apps.nowinandroid.core.model.data.NewsCategory

class NewsConverter {
    @TypeConverter
    fun fromCategory(category: NewsCategory): String =
        category.name

    @TypeConverter
    fun toCategory(value: String): NewsCategory =
        NewsCategory.valueOf(value)
}