package com.google.samples.apps.nowinandroid.core.network.model.exceptions

import com.google.samples.apps.nowinandroid.core.network.interceptor.HttpStatusCode
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.MyIOExceptionErrorKeys.BAD_DECRYPT
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.MyIOExceptionErrorKeys.BAD_GATEWAY
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.MyIOExceptionErrorKeys.BAD_REQUEST
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.MyIOExceptionErrorKeys.CLIENT_ERROR
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.MyIOExceptionErrorKeys.FORBIDDEN
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.MyIOExceptionErrorKeys.GATEWAY_TIMEOUT
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.MyIOExceptionErrorKeys.INTERNAL_SERVER_ERROR
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.MyIOExceptionErrorKeys.NOT_FOUND
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.MyIOExceptionErrorKeys.NO_CONNECTIVITY
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.MyIOExceptionErrorKeys.RATE_LIMIT
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.MyIOExceptionErrorKeys.REQUEST_TIMEOUT
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.MyIOExceptionErrorKeys.SERVER_ERROR
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.MyIOExceptionErrorKeys.SERVICE_UNAVAILABLE
import com.google.samples.apps.nowinandroid.core.network.model.exceptions.MyIOExceptionErrorKeys.UNAUTHORIZED
import java.io.IOException

abstract class MyIOException : IOException() {
    abstract val statusCode: Int?
    abstract val errorKey: String
}

class NoConnectivityException : MyIOException() {
    override val statusCode: Int? = null
    override val errorKey = NO_CONNECTIVITY
    override val message = "No Internet Connection"
}

class BadRequestException : MyIOException() {
    override val statusCode = HttpStatusCode.BAD_REQUEST
    override val errorKey = BAD_REQUEST
    override val message = "Bad Request (400)"
}

class UnauthorizedException : MyIOException() {
    override val statusCode = HttpStatusCode.UNAUTHORIZED
    override val errorKey = UNAUTHORIZED
    override val message = "Unauthorized (401)"
}

class ForbiddenException : MyIOException() {
    override val statusCode = HttpStatusCode.FORBIDDEN
    override val errorKey = FORBIDDEN
    override val message = "Forbidden (403)"
}

class NotFoundException : MyIOException() {
    override val statusCode = HttpStatusCode.NOT_FOUND
    override val errorKey = NOT_FOUND
    override val message = "Not Found (404)"
}

class RequestTimeoutException : MyIOException() {
    override val statusCode = HttpStatusCode.REQUEST_TIMEOUT
    override val errorKey = REQUEST_TIMEOUT
    override val message = "Request Timeout (408)"
}

class RateLimitExceededException : MyIOException() {
    override val statusCode = HttpStatusCode.TOO_MANY_REQUESTS
    override val errorKey = RATE_LIMIT
    override val message = "Rate Limit Exceeded (429)"
}

class InternalServerErrorException : MyIOException() {
    override val statusCode = HttpStatusCode.INTERNAL_SERVER_ERROR
    override val errorKey = INTERNAL_SERVER_ERROR
    override val message = "Internal Server Error (500)"
}

class BadGatewayException : MyIOException() {
    override val statusCode = HttpStatusCode.BAD_GATEWAY
    override val errorKey = BAD_GATEWAY
    override val message = "Bad Gateway (502)"
}

class ServiceUnavailableException : MyIOException() {
    override val statusCode = HttpStatusCode.SERVICE_UNAVAILABLE
    override val errorKey = SERVICE_UNAVAILABLE
    override val message = "Service Unavailable (503)"
}

class GatewayTimeoutException : MyIOException() {
    override val statusCode = HttpStatusCode.GATEWAY_TIMEOUT
    override val errorKey = GATEWAY_TIMEOUT
    override val message = "Gateway Timeout (504)"
}

class ClientErrorException(
    override val statusCode: Int,
) : MyIOException() {
    override val errorKey = CLIENT_ERROR
    override val message = "Client Error ($statusCode)"
}

class ServerErrorException(
    override val statusCode: Int,
) : MyIOException() {
    override val errorKey = SERVER_ERROR
    override val message = "Server Error ($statusCode)"
}

class BadDecryptException : MyIOException() {
    override val statusCode: Int? = null
    override val errorKey = BAD_DECRYPT
    override val message = "Failed to decrypt response"
}