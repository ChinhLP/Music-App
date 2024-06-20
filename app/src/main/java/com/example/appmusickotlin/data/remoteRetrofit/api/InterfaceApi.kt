package com.example.appmusickotlin.data.remoteRetrofit.api

import com.example.appmusickotlin.data.remoteRetrofit.model.MusicRemote
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("Remote_audio.json")
    fun getSongs(): Call<MutableList<MusicRemote>>
}

