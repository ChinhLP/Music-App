package com.example.appmusickotlin.data.remoteRetrofit.Result

sealed class State<out R> {
    data class Success<out T>(val data: T) : State<T>()
    data class Error(val exception: Throwable) : State<Nothing>()
    object Loading : State<Nothing>()
}