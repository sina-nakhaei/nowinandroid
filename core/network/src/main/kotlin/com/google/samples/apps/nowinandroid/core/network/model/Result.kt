/*
 * Copyright 2026 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.nowinandroid.core.network.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

sealed interface Result<out T> {
    data class Success<out T>(
        val data: T?,
    ) : Result<T>

    data object Loading : Result<Nothing>

    data class Error(
        val statusCode: Int? = null,
        val message: String? = null,
        val throwable: Throwable? = null,
    ) : Result<Nothing>
}

suspend fun <T> Result<T>.doOnEachState(
    onSuccess: suspend (Result.Success<T>) -> Unit,
    onError: suspend (Result.Error) -> Unit,
) {
    when (this) {
        is Result.Error -> onError(this)
        is Result.Success<T> -> onSuccess(this)
        else -> {}
    }
}

fun <T> Flow<Result<T>>.onError(
    block: suspend (Result.Error) -> Unit,
) = onEach {
    if (it is Result.Error)
        block(it)
}

fun <T> Flow<Result<T>>.onSuccess(
    block: suspend (Result.Success<T>) -> Unit,
) = onEach {
    if (it is Result.Success)
        block(it)
}

fun <T> Flow<Result<T>>.onLoadingChange(
    block: suspend (Boolean) -> Unit,
) = onEach {
    block(it is Result.Loading)
}