package com.example.appmusickotlin.service.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ServiceViewmodel : ViewModel() {
    private var _isPlay = MutableLiveData<Boolean>()
    var isPlay: LiveData<Boolean> = _isPlay

    init {
        _isPlay.value = false
    }

    fun setIsPlay(isPlay: Boolean) {
        _isPlay.value = isPlay
    }
}