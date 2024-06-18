package com.example.appmusickotlin.ui.home.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmusickotlin.model.DataListPlayList
import com.example.appmusickotlin.model.Song
import com.example.appmusickotlin.model.User
import com.example.appmusickotlin.ui.home.Fragment.HomeFragment

class HomeViewModel : ViewModel() {
    private var _user = MutableLiveData<User>()
    private var _song = MutableLiveData<Song>()
    val user: LiveData<User> get() = _user
    val song: LiveData<Song> get() = _song

    init {
        _user.value = User
    }

//    fun setUserAlbum(listMusic: DataListPlayList) {
//        _user.value?.albumsLst?.add(listMusic)
//        User.albumsLst = _user.value!!.albumsLst
//    }

//    fun setSongAlbum(song: Song) {
//
//       _song.value = song
//
//    }
}


