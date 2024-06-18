package com.example.appmusickotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appmusickotlin.db.database.AppDatabase
import com.example.appmusickotlin.db.entity.MusicLocal
import com.example.appmusickotlin.db.repository.MusicLocalRepository

class MusicLocalViewModel(application: Application) : AndroidViewModel(application)  {
    private val _musicLocalRepository : MusicLocalRepository
    var allMusicLocal: LiveData<MutableList<MusicLocal>>

    init {
        val musicLocalDao = AppDatabase.getDatabase(application).musicLocalDao()
        _musicLocalRepository = MusicLocalRepository(musicLocalDao)
        allMusicLocal = _musicLocalRepository.allMusicLocal
    }

    fun insert(musicLocal: MutableList<MusicLocal>) {
        _musicLocalRepository.insert(musicLocal)
    }
}