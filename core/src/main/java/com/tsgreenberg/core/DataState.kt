package com.tsgreenberg.core

sealed class DataState<out T> {
    data class Loading(
        val progressBarState: ProgressBarState
    ) : DataState<Nothing>()

    class Success<T>(
        val data: T
    ) : DataState<T>()

    data class Error(
        val msg: String
    ) : DataState<Nothing>()
}