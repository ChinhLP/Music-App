package com.example.appmusickotlin.data.local.db.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appmusickotlin.data.local.db.database.AppDatabase
import com.example.appmusickotlin.data.local.db.entity.PlaylistEntity
import com.example.appmusickotlin.data.local.db.entity.UserEntity
import com.example.appmusickotlin.data.local.db.repository.PlaylistRepository
import com.example.appmusickotlin.data.local.db.repository.UserRepository
import com.example.appmusickotlin.model.DataListPlayList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PlaylistViewModel(application: Application) : AndroidViewModel(application) {

    private val _playlistRepository: PlaylistRepository
    val allPlaylist: LiveData<MutableList<PlaylistEntity>>

   // private var _playlist = MutableLiveData<MutableList<DataListPlayList>>()

    var playlist : LiveData<MutableList<DataListPlayList>>? = null

    init {
        val playlistDao = AppDatabase.getDatabase(application).playlistDao()
        _playlistRepository = PlaylistRepository(playlistDao)
        allPlaylist = _playlistRepository.allPlaylists
    }

    fun insert(playlist: DataListPlayList) = viewModelScope.launch{
        _playlistRepository.insert(playlist)
    }

    fun getPlaylist(userId : Long) = viewModelScope.launch{
        playlist = _playlistRepository.getPlaylist(userId)
        Log.d("ppp" , playlist.toString())

    }

    fun deletePlaylist(userId : Long) = viewModelScope.launch{
        _playlistRepository.delete(userId)
    }

    fun updateNamePlaylist(id : Long,name : String) = viewModelScope.launch {
        val newPlaylist = _playlistRepository.getPlaylistById(id)
        newPlaylist.title = name
        _playlistRepository.updatePlaylistName(newPlaylist)
    }
    fun updateNumberMusicPlaylist(id : Long,sum : Boolean) = viewModelScope.launch {
        if(sum == true){
            val newPlaylist = _playlistRepository.getPlaylistById(id)
            newPlaylist.numberMusic = newPlaylist.numberMusic?.plus(1)
            Log.d("ddsaa","${newPlaylist.numberMusic}")
            _playlistRepository.updatePlaylistName(newPlaylist)
        } else {
                val newPlaylist = _playlistRepository.getPlaylistById(id)
                newPlaylist.numberMusic = newPlaylist.numberMusic?.minus(1)
                Log.d("ddsaa","${newPlaylist.numberMusic}")
                _playlistRepository.updatePlaylistName(newPlaylist)

        }

    }

}