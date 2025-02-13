package com.example.appmusickotlin.data.remoteRetrofit.api

import com.example.appmusickotlin.data.remoteRetrofit.model.TopArtistsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistApiService {
    @GET("2.0/")
    fun getTopArtists(
        @Query("api_key") apiKey: String,
        @Query("format") format: String,
        @Query("method") method: String
    ): Call<TopArtistsResponse>
}