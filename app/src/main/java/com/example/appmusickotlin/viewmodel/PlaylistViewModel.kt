package com.example.appmusickotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appmusickotlin.db.database.AppDatabase
import com.example.appmusickotlin.db.entity.PlaylistEntity
import com.example.appmusickotlin.db.entity.UserEntity
import com.example.appmusickotlin.db.repository.PlaylistRepository
import com.example.appmusickotlin.db.repository.UserRepository
import com.example.appmusickotlin.model.DataListPlayList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PlaylistViewModel(application: Application) : AndroidViewModel(application) {

    private val _playlistRepository: PlaylistRepository
    val allPlaylist: LiveData<MutableList<PlaylistEntity>>

    private var _playlist = MutableLiveData<MutableList<DataListPlayList>>()

    var playlist : LiveData<MutableList<DataListPlayList>>  = _playlist
    init {
        val playlistDao = AppDatabase.getDatabase(application).playlistDao()
        _playlistRepository = PlaylistRepository(playlistDao)
        allPlaylist = _playlistRepository.allPlaylists
    }

    fun insert(playlist: DataListPlayList) = viewModelScope.launch{
        _playlistRepository.insert(playlist)
    }
    fun getPlaylist(userId : Long) = viewModelScope.launch{
        _playlist.value = _playlistRepository.getPlaylist(userId)
    }
    fun deletePlaylist(userId : Long) = viewModelScope.launch{
        _playlistRepository.delete(userId)
    }
}