package com.example.appmusickotlin.data.remoteRetrofit.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmusickotlin.data.remoteRetrofit.Result.State
import com.example.appmusickotlin.data.remoteRetrofit.model.Artist
import com.example.appmusickotlin.data.remoteRetrofit.model.TopArtistsResponse
import com.example.appmusickotlin.data.remoteRetrofit.repository.ArtistRepository

class ArtistViewModel : ViewModel() {
    private val artistRepository = ArtistRepository()
    private val _artists = MutableLiveData<State<MutableList<Artist>>?>()
    private val _allArtists = MutableLiveData<State<MutableList<Artist>>?>()


    val artist: LiveData<State<MutableList<Artist>>?> get() = _artists
    val allArtists: LiveData<State<MutableList<Artist>>?> get() = _allArtists


    fun fetchTop5Artists(apiKey: String, format: String, method: String, limit: Int = 5) {
        _artists.value = State.Loading
        artistRepository.getTopArtists(apiKey, format, method) { artistList, error ->
            if (error != null) {
                _artists.value = State.Error(error)
            } else if (artistList != null) {
                val limitedArtistList = artistList.take(limit).toMutableList()
                _artists.value = State.Success(limitedArtistList)
            }
        }
    }
    fun fetchTopArtists(apiKey: String, format: String, method: String) {
        _allArtists.value = State.Loading
        artistRepository.getTopArtists(apiKey, format, method) { artistList, error ->
            if (error != null) {
                _allArtists.value = State.Error(error)
            } else if (artistList != null) {
                _allArtists.value = State.Success(artistList)
            }
        }
    }

}