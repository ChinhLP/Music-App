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
    private val _artists = MutableLiveData<State<MutableList<Artist>>>()

    val artist: LiveData<State<MutableList<Artist>>> get() = _artists

    fun fetchTopArtist(apiKey: String, format: String, method: String,mbid: String) {
        _artists.value = State.Loading
        artistRepository.getTopArtists() { artistList, error ->
            if (error != null) {
                _artists.value = State.Error(error)
            } else if (artistList != null) {
                _artists.value = State.Success(artistList)
            }
        }
    }
}