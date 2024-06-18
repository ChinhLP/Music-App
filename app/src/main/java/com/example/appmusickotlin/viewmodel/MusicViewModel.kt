package com.example.appmusickotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appmusickotlin.db.database.AppDatabase
import com.example.appmusickotlin.db.entity.MusicEntity
import com.example.appmusickotlin.db.entity.PlaylistEntity
import com.example.appmusickotlin.db.repository.MusicRepository
import com.example.appmusickotlin.db.repository.PlaylistRepository
import com.example.appmusickotlin.model.Song
import com.example.appmusickotlin.model.User
import kotlinx.coroutines.launch

class MusicViewModel(application: Application) : AndroidViewModel(application)  {
    private val _musicRepository: MusicRepository
    val allMusic: LiveData<MutableList<MusicEntity>>

    private var _music = MutableLiveData<MutableList<Song>>()

    var music : LiveData<MutableList<Song>>  = _music

    init {
        val musicDao = AppDatabase.getDatabase(application).musicDao()
        _musicRepository = MusicRepository(musicDao)
        allMusic = _musicRepository.allMusics
    }

    fun insert(musicEntity: Song) = viewModelScope.launch {
        _musicRepository.insert(musicEntity)
    }

    fun delete(id: Long) = viewModelScope.launch {
        _musicRepository.delete(id)
    }

    fun getMusic(id: Long) = viewModelScope.launch {
        _music.value = _musicRepository.getMusic(id)
    }
    fun deleteAll(playlistId : Long) = viewModelScope.launch {
        _musicRepository.deleteAll(playlistId)
    }

}