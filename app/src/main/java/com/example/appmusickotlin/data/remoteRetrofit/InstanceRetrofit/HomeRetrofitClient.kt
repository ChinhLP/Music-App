package com.example.appmusickotlin.data.remoteRetrofit.InstanceRetrofit

import com.example.appmusickotlin.data.remoteRetrofit.api.AlbumApiService
import com.example.appmusickotlin.data.remoteRetrofit.api.ArtistApiService
import com.example.appmusickotlin.data.remoteRetrofit.api.TrackApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HomeRetrofitClient {
    private const val BASE_URL = "https://ws.audioscrobbler.com/"


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    val artistApiService: ArtistApiService by lazy {
        retrofit.create(ArtistApiService::class.java)
    }
    val albumApiService: AlbumApiService by lazy {
        retrofit.create(AlbumApiService::class.java)
    }
    val trackApiService: TrackApiService by lazy {
        retrofit.create(TrackApiService::class.java)
    }
}