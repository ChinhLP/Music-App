package com.example.appmusickotlin.data.local.db.mapper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.appmusickotlin.data.local.db.entity.MusicEntity
import com.example.appmusickotlin.model.Song

fun LiveData<MutableList<MusicEntity>>.toSongListLiveData(): LiveData<MutableList<Song>> {
    val result = MediatorLiveData<MutableList<Song>>()
    result.addSource(this) { musicEntities ->
        val songList = musicEntities.map { musicEntity ->
            Song(
                id = musicEntity.id,
                name = musicEntity.title,
                duration = musicEntity.duration,
                albumId = musicEntity.albumId,
                artist = musicEntity.artist,
                path = musicEntity.data,
                playlistId = musicEntity.playlistId
            )
        }.toMutableList()
        result.value = songList
    }
    return result
}

fun Song.toMusicEntity(): MusicEntity {
    return MusicEntity(
        id = this.id,
        title = this.name ?: "",
        duration = this.duration ?: 0,
        albumId = this.albumId ?: 0,
        artist = this.artist ?: "",
        data = this.path ?: "",
        playlistId = this.playlistId ?: 0
    )
}