package com.example.appmusickotlin.model


data class User(
    var username: String? = "",
    var email: String? = "",
    var phoneNumber: String? = "",
    var password: String? = "",
    var rePassword: String? = ""
)
