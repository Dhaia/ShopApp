package com.example.shopapp2.common

import com.example.shopapp2.common.GenericErrors.ERROR_UNKNOWN
import com.example.shopapp2.common.NetworkErrors.NETWORK_ERROR_TIMEOUT
import com.example.shopapp2.common.NetworkErrors.NETWORK_ERROR_UNKNOWN
import kotlinx.coroutines.*
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

const val NETWORK_TIMEOUT = 6000L

suspend fun <T> safeNetworkCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T?,
): NetworkResult<T?> {
    return withContext(dispatcher) {
        try {
            // throws TimeoutCancellationException
            withTimeout(NETWORK_TIMEOUT) {
                NetworkResult.Success(apiCall.invoke())
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            when (throwable) {
                is TimeoutCancellationException -> {
                    val code = 408 // timeout error code
                    NetworkResult.GenericError(code, NETWORK_ERROR_TIMEOUT)
                }
                is IOException -> {
                    NetworkResult.NetworkError
                }
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    Timber.d(errorResponse)
                    NetworkResult.GenericError(
                        code,
                        errorResponse
                    )
                }
                else -> {
                    Timber.d(NETWORK_ERROR_UNKNOWN)
                    NetworkResult.GenericError(
                        null,
                        NETWORK_ERROR_UNKNOWN
                    )
                }
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): String? {
    return try {
        throwable.response()?.errorBody()?.string()
    } catch (exception: Exception) {
        ERROR_UNKNOWN
    }
}

