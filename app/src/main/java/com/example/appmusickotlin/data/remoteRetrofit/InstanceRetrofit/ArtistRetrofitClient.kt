package com.example.appmusickotlin.data.remoteRetrofit.InstanceRetrofit

import com.example.appmusickotlin.data.remoteRetrofit.api.ArtistApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ArtistRetrofitClient {
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
}