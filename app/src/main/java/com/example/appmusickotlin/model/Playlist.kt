package com.example.appmusickotlin.model




data class DataListPlayList(
    val title : String,
    var listMusic : MutableList<Song>? = null
)

object ListAlbums {
    var albumList : MutableList<DataListPlayList>? = null
}