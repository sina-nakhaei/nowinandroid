package com.google.samples.apps.nowinandroid.core.network

import com.google.samples.apps.nowinandroid.core.network.model.NetworkNewsFeed
import com.google.samples.apps.nowinandroid.core.network.model.Result

interface NewsNetworkDataSource {
    suspend fun getNewsFeed(): Result<NetworkNewsFeed>
}

