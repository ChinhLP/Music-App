package com.example.appmusickotlin.db.mapper

import com.example.appmusickotlin.db.entity.MusicEntity
import com.example.appmusickotlin.model.Song

fun MutableList<MusicEntity>.toSongList(): MutableList<Song> {
    return this.map { musicEntity ->
        Song(
            id = musicEntity.id,
            name = musicEntity.title,
            duration = musicEntity.duration,
            albumId = musicEntity.albumId,
            artist = musicEntity.artist,
            data = musicEntity.data,
            playlistId = musicEntity.playlistId
        )
    }.toMutableList()
}

fun Song.toMusicEntity(): MusicEntity {
    return MusicEntity(
        id = this.id,
        title = this.name ?: "",
        duration = this.duration ?: 0,
        albumId = this.albumId ?: 0,
        artist = this.artist ?: "",
        data = this.data ?: "",
        playlistId = this.playlistId ?: 0
    )
}