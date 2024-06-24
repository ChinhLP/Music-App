package com.example.appmusickotlin.data.remoteRetrofit.model

data class Album (
    val name: String? = null,
    val playcount: Int? = 0,
    val url: String? = null,
    val artist: Artist? = null,
    val image: List<String>? = null,
)