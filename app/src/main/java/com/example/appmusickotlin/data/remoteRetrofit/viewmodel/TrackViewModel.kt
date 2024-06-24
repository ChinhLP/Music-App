package com.example.appmusickotlin.data.remoteRetrofit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmusickotlin.data.remoteRetrofit.Result.State
import com.example.appmusickotlin.data.remoteRetrofit.model.Track
import com.example.appmusickotlin.data.remoteRetrofit.repository.TrackRepository

class TrackViewModel : ViewModel() {
    private val trackRepository = TrackRepository()
    private val _track = MutableLiveData<State<MutableList<Track>>>()

    val track : LiveData<State<MutableList<Track>>> get() = _track

    fun getTop5tracks (apiKey : String , format : String, method : String, mbid : String , limit : Int = 5){
        _track.value = State.Loading
        trackRepository.getTopTrack(apiKey, format, method, mbid){ albumList,error ->
            if(error != null){
                _track.value = State.Error(error)
            } else if (albumList != null) {
                val limitedArtistList = albumList.take(limit).toMutableList()
                _track.value = State.Success(limitedArtistList)
            }
        }
    }
}