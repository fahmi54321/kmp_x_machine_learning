package com.kmpxmachinelearning.shared.core.network

import com.kmpxmachinelearning.shared.core.error.NetworkException
import com.kmpxmachinelearning.shared.core.error.NotFoundException
import com.kmpxmachinelearning.shared.core.error.ServerException
import com.kmpxmachinelearning.shared.core.error.TimeoutException
import com.kmpxmachinelearning.shared.core.error.UnauthorizedException
import com.kmpxmachinelearning.shared.core.error.UnknownException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.io.IOException
import kotlinx.serialization.json.Json

fun provideHttpClient(): HttpClient {
    return HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }

        install(Logging) {
            level = LogLevel.ALL
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 15000
        }

        HttpResponseValidator {
            validateResponse { response ->
                val statusCode = response.status.value

                if (statusCode in 200..299) return@validateResponse

                val message = try {
                    val body = response.body<String>()
                    body
                } catch (e: Exception) {
                    "Terjadi kesalahan"
                }

                when (statusCode) {
                    401 -> throw UnauthorizedException(message)
                    404 -> throw NotFoundException(message)
                    500 -> throw ServerException("Kesalahan server")
                    else -> throw UnknownException(message)
                }
            }

            handleResponseExceptionWithRequest { cause, _ ->
                when (cause) {
                    is HttpRequestTimeoutException ->
                        throw TimeoutException()

                    is IOException ->
                        throw NetworkException()

                    else -> throw cause
                }
            }
        }

        defaultRequest {
            url("http://10.0.2.2:5000/")
            contentType(ContentType.Application.Json)
        }
    }
}