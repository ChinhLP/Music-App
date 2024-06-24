package com.example.appmusickotlin.data.remoteRetrofit.model

import com.example.appmusickotlin.model.Image
import com.google.gson.annotations.SerializedName

data class Track (
    val name: String? = null,
    val playcount: Int? = 0,
    val listeners: Int? = 0,
    val url: String? = null,
    val streamable: String? = null,
    val artist: Artist? = null,
    val image: List<Image>? = null,
    val rank: Int? = 0
)

data class ImageTrack(
    @SerializedName("#text") val text: String,
    val size: String
)
data class TopTrackResponse(
    @SerializedName("toptracks") val track : Tracks
)

data class Tracks(
    @SerializedName("track") val listTrack : MutableList<Track>
)