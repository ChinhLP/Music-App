package com.example.appmusickotlin.data.remoteRetrofit.model

import com.google.gson.annotations.SerializedName

data class Artist(
    val name: String? = null,
    val playcount: Long? = 0,
    val listeners: Long? = 0,
    val mbid: String? = null,
    val url: String? = null,
    val streamable: String? = null,
    val image: List<ImageArtist>? = null,
)

data class ImageArtist(
    @SerializedName("#text") val text: String,
    val size: String
)

data class TopArtistsResponse(
    @SerializedName("artists") val artists: Artists
)

data class Artists(
    @SerializedName("artist") val artistList: MutableList<Artist>
)