package com.example.appmusickotlin.data.local.db.repository

import androidx.lifecycle.LiveData
import com.example.appmusickotlin.data.local.db.dao.MusicDao
import com.example.appmusickotlin.data.local.db.entity.MusicEntity
import com.example.appmusickotlin.data.local.db.mapper.toMusicEntity
import com.example.appmusickotlin.data.local.db.mapper.toSongListLiveData
import com.example.appmusickotlin.model.Song
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MusicRepository(private val musicDao: MusicDao) {
    private val listMusicsEntity = musicDao.getAllMusics()

    val allMusics: LiveData<MutableList<Song>> = listMusicsEntity.toSongListLiveData()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    suspend fun insert(music: Song) {
        coroutineScope.launch {
            val musicEntity = music.toMusicEntity()
            musicDao.insertMusic(musicEntity)
        }
    }
    suspend fun getMusic(albumId: Long): LiveData<MutableList<Song>> {
        return withContext(Dispatchers.IO) {
            val listMusicEntity = musicDao.getMusicsByPlaylistId(albumId)
            val song = listMusicEntity.toSongListLiveData()
            song
        }
    }

    suspend fun delete(id: Long) {
        coroutineScope.launch(Dispatchers.IO) {
            musicDao.deleteMusic(id)
        }
    }

    suspend fun deleteAll(playlistId : Long) {
        coroutineScope.launch(Dispatchers.IO) {
            musicDao.deleteAllMusic(playlistId)
        }
    }
    suspend fun getAllMusic() : LiveData<MutableList<Song>> {
        return withContext(Dispatchers.IO) {
            val listMusicEntity = musicDao.getAllMusics()
            val song = listMusicEntity.toSongListLiveData()
            song
        }
    }

}