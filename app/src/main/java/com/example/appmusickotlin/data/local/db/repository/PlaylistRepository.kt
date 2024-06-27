package com.example.appmusickotlin.data.local.db.repository

import androidx.lifecycle.LiveData
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.appmusickotlin.data.local.db.dao.PlaylistDao
import com.example.appmusickotlin.data.local.db.entity.PlaylistEntity
import com.example.appmusickotlin.data.local.db.mapper.toDataListPlayList
import com.example.appmusickotlin.data.local.db.mapper.toDataListPlayListLiveData
import com.example.appmusickotlin.data.local.db.mapper.toPlaylistEntity
import com.example.appmusickotlin.model.DataListPlayList
import com.example.appmusickotlin.model.User.userId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlaylistRepository(private val playlistDao : PlaylistDao) {
    val allPlaylists : LiveData<MutableList<PlaylistEntity>> = playlistDao.getAllUsers()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    suspend fun insert(playlist : DataListPlayList){
        coroutineScope.launch {
            val playlistEntity = playlist.toPlaylistEntity()
            playlistDao.insertPlaylist(playlistEntity)
        }
    }
    suspend fun getPlaylist(userId : Long) : LiveData<MutableList<DataListPlayList>> {
        return withContext(Dispatchers.IO) {
            val playlistEntities =  playlistDao.getPlaylistsByUserId(userId)
            val dataListPlayLists: LiveData<MutableList<DataListPlayList>> = playlistEntities.toDataListPlayListLiveData()
             dataListPlayLists
        }

    }

    suspend fun delete(id: Long) {
        coroutineScope.launch {
            playlistDao.deletePlaylist(id)
        }
    }

    suspend fun updatePlaylistName(playlist : DataListPlayList) {
        coroutineScope.launch {
            val playlistEntity = playlist.toPlaylistEntity()
            playlistDao.updatePlaylistName(playlistEntity)
        }

    }


    suspend fun getPlaylistById(id : Long) : DataListPlayList {

        return withContext(Dispatchers.IO) {
            val playlist =  playlistDao.getPlaylistById(id)
            val playlistData = playlist!!.toDataListPlayList()
            playlistData
        }

    }





}