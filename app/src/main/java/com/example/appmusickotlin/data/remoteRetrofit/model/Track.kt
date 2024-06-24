package com.example.appmusickotlin.data.remoteRetrofit.model

data class Track (
    val name: String? = null,
    val playcount: Int? = 0,
    val listeners: Int? = 0,
    val url: String? = null,
    val streamable: String? = null,
    val artist: Artist? = null,
    val image: List<String>? = null,
    val rank: Int? = 0
)