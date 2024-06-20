package com.example.appmusickotlin.retrofit2.viewmodel

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmusickotlin.model.Song
import com.example.appmusickotlin.retrofit2.Result.State
import com.example.appmusickotlin.retrofit2.repository.MusicRemoteRepository
class MusicRemoteViewModel : ViewModel() {

    private val musicRepository = MusicRemoteRepository()
    private val _musicList = MutableLiveData<State<MutableList<Song>?>>()


    val musicList: MutableLiveData<State<MutableList<Song>?>> get() = _musicList

    fun fetchAllMusic() {
        val handler = Handler(Looper.getMainLooper())
        handler.removeCallbacksAndMessages(null)

        _musicList.postValue(State.Loading)
        handler.postDelayed({
            musicRepository.getMusic { musicList, error ->
                if (error == null) {
                    _musicList.value = State.Success(musicList?.toMutableList() ?: mutableListOf())
                } else {
                    _musicList.value = State.Error(error)
                    Log.e("MusicRemoteViewModel", "Error fetching music list: $error")
                    // Handle error here if needed
                }
            }
        }, 200)
    }
}