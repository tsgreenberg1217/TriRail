package com.tsgreenberg.core

sealed class DataState<out T> {
    object Loading : DataState<Nothing>()

    class Success<T>(
        val data: T
    ) : DataState<T>()

    data class Error(
        val msg: String
    ) : DataState<Nothing>()
}