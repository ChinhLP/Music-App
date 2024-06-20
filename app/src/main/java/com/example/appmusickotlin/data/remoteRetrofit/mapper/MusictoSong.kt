package com.example.appmusickotlin.data.remoteRetrofit.mapper

import com.example.appmusickotlin.model.Song
import com.example.appmusickotlin.data.remoteRetrofit.model.MusicRemote

// Hàm mở rộng từ MusicRemote sang Song
fun MusicRemote.toSong(): Song {
    return Song(
        name = this.title,
        artist = this.artist,
        kind = this.kind,
        duration = this.duration.toLongOrNull() ?: 0,
        path = this.path
    )
}