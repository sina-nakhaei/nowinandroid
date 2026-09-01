package com.google.samples.apps.nowinandroid.core.network.retrofit

import com.google.samples.apps.nowinandroid.core.network.NewsNetworkDataSource
import com.google.samples.apps.nowinandroid.core.network.model.NetworkNewsFeed
import com.google.samples.apps.nowinandroid.core.network.model.Result
import com.google.samples.apps.nowinandroid.core.network.model.apiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitNewsApi {
    @GET("api/v1/cors/news-feed")
    suspend fun getNewsFeed(): NetworkNewsFeed
}

@Singleton
internal class RetrofitNewsNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : NewsNetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl("https://ok.surf/")
        .callFactory { okhttpCallFactory.get().newCall(it) }
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()),
        )
        .build()
        .create(RetrofitNewsApi::class.java)

    override suspend fun getNewsFeed(): Result<NetworkNewsFeed> =
        apiCall {
            networkApi.getNewsFeed()
        }
}