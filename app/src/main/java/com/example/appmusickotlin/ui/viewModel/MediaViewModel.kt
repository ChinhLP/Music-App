package com.example.appmusickotlin.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MediaViewModel : ViewModel() {
    private val _isPlaying = MutableLiveData<Boolean>()
    val isPlaying : LiveData<Boolean> get() = _isPlaying

    fun setPlayingState(isPlaying: Boolean) {
        _isPlaying.value = isPlaying
    }

}
