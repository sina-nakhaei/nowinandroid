package com.google.samples.apps.nowinandroid.core.network.model

import com.google.samples.apps.nowinandroid.core.network.model.exceptions.MyIOException

suspend fun <T> apiCall(
    block: suspend () -> T,
): Result<T> {
    return try {
        Result.Success(block())
    } catch (e: MyIOException) {
        Result.Error(
            statusCode = e.statusCode,
            message = e.message,
            throwable = e,
        )
    } catch (e: Exception) {
        Result.Error(
            statusCode = null,
            message = "Unknown Error!",
            throwable = e,
        )
    }
}