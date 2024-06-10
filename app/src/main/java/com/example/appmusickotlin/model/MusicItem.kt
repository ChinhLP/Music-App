package com.example.appmusickotlin.model

data class MusicItem(
    val albumArt: Int,
    val songTitle: String,
    val artistName: String
)

val musicList = listOf(
    MusicItem(1, "Song 1", "Artist 1"),
    MusicItem(2, "Song 2", "Artist 2"),
    MusicItem(3, "Song 3", "Artist 3"),
    // Thêm các mục khác tùy thích
)