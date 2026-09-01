package com.google.samples.apps.nowinandroid.feature.news.impl

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.google.samples.apps.nowinandroid.core.model.data.News
import com.google.samples.apps.nowinandroid.feature.news.impl.NewsDetailViewModel.Factory

@Composable
internal fun NewsDetailScreen(
    newsId: String,
    modifier: Modifier = Modifier,
) {
    val viewModel = hiltViewModel<NewsDetailViewModel, Factory>(
        creationCallback = { factory ->
            factory.create(newsId)
        },
    )
    val news by viewModel.news.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState()),
    ) {
        news?.let { item ->
            NewsCard(item)
        }
    }
}

@Composable
private fun NewsCard(
    item: News,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(Color.White)
            .padding(16.dp),
    ) {
        Text(
            text = item.category.name,
            color = Color(0xFF808080),
            modifier = Modifier.padding(bottom = 4.dp),
            fontSize = 12.sp,
        )

        MyImage(
            painter = rememberAsyncImagePainter(item.imageUrl),
            modifier = Modifier
                .clip(RoundedCornerShape(32.dp))
                .fillMaxWidth()
                .height(200.dp),
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = item.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraBold,
        )

        Spacer(Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item.sourceIconUrl?.let {
                MyImage(
                    painter = rememberAsyncImagePainter(it),
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .size(24.dp),
                )
            }
            Text(
                text = item.source,
                style = MaterialTheme.typography.labelLarge,
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = item.link,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun MyImage(
    painter: Painter,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(24.dp)
            .background(Color(0xFFD7D7D7)),
    )
}