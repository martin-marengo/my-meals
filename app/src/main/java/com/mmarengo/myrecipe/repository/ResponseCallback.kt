package com.mmarengo.myrecipe.repository

interface ResponseCallback<T> {

    fun onSuccess(value: T?)

    fun onGenericError(dataResponse: DataResponse.GenericError)

    fun onConnectionError(dataResponse: DataResponse.ConnectionError)
}
