package com.google.samples.apps.nowinandroid.core.network.interceptor

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.NoConnectivityException
import com.google.samples.apps.nowinandroid.core.network.util.ConnectivityUtil
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(private val context: Context) : Interceptor {
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!ConnectivityUtil.isOnline(context)) {
            throw NoConnectivityException()
        }
        return chain.proceed(chain.request())
    }
}

