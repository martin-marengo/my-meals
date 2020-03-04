package com.mmarengo.themeal.repository

import okhttp3.ResponseBody

sealed class DataResponse<out T> {

    data class Success<out T>(val value: T): DataResponse<T>()

    data class GenericError(
        val code: Int? = null,
        val errorBody: ResponseBody? = null,
        val t: Throwable? = null
    ) : DataResponse<Nothing>()

    data class ConnectionError(val t: Throwable? = null) : DataResponse<Nothing>()
}
