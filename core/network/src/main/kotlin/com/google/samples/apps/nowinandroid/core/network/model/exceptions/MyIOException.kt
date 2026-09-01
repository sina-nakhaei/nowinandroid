package com.google.samples.apps.nowinandroid.core.network.model.exceptions

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
    abstract val errorKey: String
}

class NoConnectivityException : MyIOException() {
    override val errorKey = NO_CONNECTIVITY
    override val message = "No Internet Connection"
}

class BadRequestException : MyIOException() {
    override val errorKey = BAD_REQUEST
    override val message = "Bad Request (400)"
}

class UnauthorizedException : MyIOException() {
    override val errorKey = UNAUTHORIZED
    override val message = "Unauthorized (401)"
}

class ForbiddenException : MyIOException() {
    override val errorKey = FORBIDDEN
    override val message = "Forbidden (403)"
}

class NotFoundException : MyIOException() {
    override val errorKey = NOT_FOUND
    override val message = "Not Found (404)"
}

class RequestTimeoutException : MyIOException() {
    override val errorKey = REQUEST_TIMEOUT
    override val message = "Request Timeout (408)"
}

class InternalServerErrorException : MyIOException() {
    override val errorKey = INTERNAL_SERVER_ERROR
    override val message = "Internal Server Error (500)"
}

class BadGatewayException : MyIOException() {
    override val errorKey = BAD_GATEWAY
    override val message = "Bad Gateway (502)"
}

class GatewayTimeoutException : MyIOException() {
    override val errorKey = GATEWAY_TIMEOUT
    override val message = "Gateway Timeout (504)"
}

class RateLimitExceededException : MyIOException() {
    override val errorKey = RATE_LIMIT
    override val message = "Rate Limit Exceeded (429)"
}

class ServiceUnavailableException : MyIOException() {
    override val errorKey = SERVICE_UNAVAILABLE
    override val message = "Service Unavailable (503)"
}

class ClientErrorException(statusCode: Int) : MyIOException() {
    override val errorKey = CLIENT_ERROR
    override val message = "Client Error ($statusCode)"
}

class ServerErrorException(statusCode: Int) : MyIOException() {
    override val errorKey = SERVER_ERROR
    override val message = "Server Error ($statusCode)"
}

class BadDecryptException : MyIOException() {
    override val errorKey = BAD_DECRYPT
    override val message = "Failed to decrypt response"
}