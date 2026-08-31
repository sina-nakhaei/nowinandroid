package com.google.samples.apps.nowinandroid.core.network

import com.google.samples.apps.nowinandroid.core.network.model.NetworkNewsFeed

interface NewsNetworkDataSource {
    suspend fun getNewsFeed(): NetworkNewsFeed
}

