package com.example.appmusickotlin.retrofit2.api

import com.example.appmusickotlin.model.Song
import com.example.appmusickotlin.retrofit2.model.MusicRemote
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("Remote_audio.json")
    fun getSongs(): Call<MutableList<MusicRemote>>
}

