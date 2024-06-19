package com.example.appmusickotlin.retrofit2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmusickotlin.model.Song
import com.example.appmusickotlin.retrofit2.repository.MusicRemoteRepository
class MusicRemoteViewModel : ViewModel() {

    private val musicRepository = MusicRemoteRepository()
    private val _musicList = MutableLiveData<MutableList<Song>>()
    val musicList: LiveData<MutableList<Song>> get() = _musicList

    fun fetchAllMusic() {
        musicRepository.getMusic { musicList, error ->
            if (error == null) {
                _musicList.postValue(musicList?.toMutableList() ?: mutableListOf())
            } else {
                Log.e("MusicRemoteViewModel", "Error fetching music list: $error")
                // Xử lý lỗi ở đây nếu cần
            }
        }
    }
}