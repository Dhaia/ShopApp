package com.example.shopapp2.common

sealed class NetworkResult<out T> {

    data class Success<out T>(val value: T): NetworkResult<T>()

    data class GenericError(
        val code: Int? = null,
        val errorMessage: String? = null
    ): NetworkResult<Nothing>()

    object NetworkError: NetworkResult<Nothing>()
}