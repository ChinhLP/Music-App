package com.example.appmusickotlin.data.local.db.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.appmusickotlin.data.local.db.database.AppDatabase
import com.example.appmusickotlin.data.local.db.entity.MusicEntity
import com.example.appmusickotlin.data.local.db.entity.PlaylistEntity
import com.example.appmusickotlin.data.local.db.repository.MusicRepository
import com.example.appmusickotlin.data.local.db.repository.PlaylistRepository
import com.example.appmusickotlin.model.Song
import com.example.appmusickotlin.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MusicViewModel(application: Application) : AndroidViewModel(application) {
    private val _musicRepository: MusicRepository
    private val _allMusic =  MutableLiveData<MutableList<Song>>()
    var allMusic: LiveData<MutableList<Song>> = _allMusic

    private val _music = MutableLiveData<MutableList<Song>>()


    val music: LiveData<MutableList<Song>> = _music

    init {
        val musicDao = AppDatabase.getDatabase(application).musicDao()
        _musicRepository = MusicRepository(musicDao)
        allMusic = _musicRepository.allMusics
    }

    fun insert(musicEntity: Song) = viewModelScope.launch {
        _musicRepository.insert(musicEntity)
    }

    fun delete(id: Long, playlistId: Long) = viewModelScope.launch {
        _musicRepository.delete(id)
        getMusic(playlistId)
    }

    fun getMusic(id: Long) = viewModelScope.launch {

        try {
            val musicList = _musicRepository.getMusic(id)
            musicList.observeForever { songs ->
                if (songs != null) {
                    _music.postValue(songs)
                    Log.d("ooo", "--------${_music.value}")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun getAllMusic() = viewModelScope.launch {
        try {
            val musicList = _musicRepository.getAllMusic()
            musicList.observeForever { songs ->
                if (songs != null) {
                    _allMusic.postValue(songs)
                    Log.d("ooo", "--------${_allMusic.value}")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun deleteAll(playlistId: Long) = viewModelScope.launch {
        _musicRepository.deleteAll(playlistId)
    }

}