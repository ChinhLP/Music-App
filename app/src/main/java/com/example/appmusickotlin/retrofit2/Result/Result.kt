package com.example.appmusickotlin.retrofit2.Result

sealed class State<out R> {
    data class Success<out T>(val data: T) : State<T>()
    data class Error(val exception: Throwable) : State<Nothing>()
    object Loading : State<Nothing>()
}