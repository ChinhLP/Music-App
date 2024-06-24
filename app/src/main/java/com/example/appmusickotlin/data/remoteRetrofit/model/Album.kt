package com.example.appmusickotlin.data.remoteRetrofit.model

import com.example.appmusickotlin.model.Image
import com.google.gson.annotations.SerializedName

data class Album (
    val name: String? = null,
    val playcount: Int? = 0,
    val url: String? = null,
    val artist: Artist? = null,
    val image: List<Image>? = null,
)

data class ImageAlbum(
    @SerializedName("#text") val text: String,
    val size: String
)

data class TopAlbumsResponse(
    @SerializedName("topalbums") val albums: Albums
)

data class Albums(
    @SerializedName("album") val albumList : MutableList<Album>
)