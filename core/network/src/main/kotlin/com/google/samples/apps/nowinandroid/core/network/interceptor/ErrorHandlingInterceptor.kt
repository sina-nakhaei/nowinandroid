package com.google.samples.apps.nowinandroid.core.network.interceptor

import android.content.res.Resources.NotFoundException
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.BadGatewayException
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.BadRequestException
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.ClientErrorException
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.ForbiddenException
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.GatewayTimeoutException
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.InternalServerErrorException
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.RateLimitExceededException
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.RequestTimeoutException
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.ServerErrorException
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.ServiceUnavailableException
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.UnauthorizedException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ErrorHandlingInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        when (response.code) {
            in 400..499 -> handleClientErrors(response)
            in 500..599 -> handleServerErrors(response)
        }

        return response
    }

    private fun handleClientErrors(response: Response) {
        when (response.code) {
            HttpStatusCode.BAD_REQUEST -> throw BadRequestException()
            HttpStatusCode.UNAUTHORIZED -> throw UnauthorizedException()
            HttpStatusCode.FORBIDDEN -> throw ForbiddenException()
            HttpStatusCode.NOT_FOUND -> throw NotFoundException()
            HttpStatusCode.REQUEST_TIMEOUT -> throw RequestTimeoutException()
            HttpStatusCode.TOO_MANY_REQUESTS -> throw RateLimitExceededException()
            else -> throw ClientErrorException(response.code)
        }
    }

    private fun handleServerErrors(response: Response) {
        when (response.code) {
            HttpStatusCode.INTERNAL_SERVER_ERROR -> throw InternalServerErrorException()
            HttpStatusCode.BAD_GATEWAY -> throw BadGatewayException()
            HttpStatusCode.SERVICE_UNAVAILABLE -> throw ServiceUnavailableException()
            HttpStatusCode.GATEWAY_TIMEOUT -> throw GatewayTimeoutException()
            else -> throw ServerErrorException(response.code)
        }
    }
}

object HttpStatusCode {
    const val BAD_REQUEST = 400
    const val UNAUTHORIZED = 401
    const val FORBIDDEN = 403
    const val NOT_FOUND = 404
    const val REQUEST_TIMEOUT = 408
    const val TOO_MANY_REQUESTS = 429
    const val INTERNAL_SERVER_ERROR = 500
    const val BAD_GATEWAY = 502
    const val SERVICE_UNAVAILABLE = 503
    const val GATEWAY_TIMEOUT = 504
}