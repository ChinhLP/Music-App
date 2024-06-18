package com.example.appmusickotlin.db.repository

import androidx.lifecycle.LiveData
import com.example.appmusickotlin.db.dao.MusicLocalDao
import com.example.appmusickotlin.db.entity.MusicEntity
import com.example.appmusickotlin.db.entity.MusicLocal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MusicLocalRepository(private val musicLocalDao: MusicLocalDao) {
    val allMusicLocal: LiveData<MutableList<MusicLocal>> = musicLocalDao.getAllMusic()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

     fun insert(musicLocal: MutableList<MusicLocal>) {
        coroutineScope.launch {
            musicLocalDao.insertAll(musicLocal)
        }

    }

    suspend fun getAllMusic(): LiveData<MutableList<MusicLocal>> {
        return withContext(Dispatchers.IO) {
            musicLocalDao.getAllMusic()
        }
    }

}