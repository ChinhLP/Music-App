package com.example.appmusickotlin.data.remoteRetrofit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmusickotlin.data.remoteRetrofit.Result.State
import com.example.appmusickotlin.data.remoteRetrofit.model.Album
import com.example.appmusickotlin.data.remoteRetrofit.model.Albums
import com.example.appmusickotlin.data.remoteRetrofit.repository.AlbumRepository

class AlbumViewModel : ViewModel() {
    private val albumRepository = AlbumRepository()
    private val _album = MutableLiveData<State<MutableList<Album>>>()

     val album : LiveData<State<MutableList<Album>>> get() = _album

    fun getTop6Albums (apiKey : String , format : String, method : String, mbid : String , limit : Int = 6){
        _album.value = State.Loading
        albumRepository.getTopAlbums(apiKey, format, method, mbid){ albumList,error ->
            if(error != null){
                _album.value = State.Error(error)
            } else if (albumList != null) {
                val limitedArtistList = albumList.take(limit).toMutableList()
                _album.value = State.Success(limitedArtistList)
            }
        }
    }
}