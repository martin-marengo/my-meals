package com.mmarengo.myrecipe.repository

interface ResponseCallback<T> {

    fun onSuccess(value: T?)

    fun onError(errorType: ErrorType, errorCode: Int? = null, t: Throwable? = null)
}
