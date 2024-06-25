package com.example.appmusickotlin.model

import com.example.appmusickotlin.data.remoteRetrofit.Result.State
import com.example.appmusickotlin.data.remoteRetrofit.model.Album
import com.example.appmusickotlin.data.remoteRetrofit.model.Artist
import com.example.appmusickotlin.data.remoteRetrofit.model.Track

sealed class ChildItem {
    data class TypeAlbum(val data: MutableList<Album>?) : ChildItem()
    data class TypeTracks(val data: MutableList<Track>?) : ChildItem()
    data class TypeArtist(val data: MutableList<Artist>?) : ChildItem()
}

