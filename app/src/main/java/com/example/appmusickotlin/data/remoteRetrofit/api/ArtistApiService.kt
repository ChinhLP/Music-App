package com.example.appmusickotlin.data.remoteRetrofit.api

import com.example.appmusickotlin.data.remoteRetrofit.model.TopArtistsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistApiService {
    @GET("2.0/?api_key=e65449d181214f936368984d4f4d4ae8&format=json&method=chart.gettopartists")
    fun getTopArtists(
//        @Query("api_key") apiKey: String,
//        @Query("format") format: String,
//        @Query("method") method: String,
//        @Query("limit") limit: Int,
//        @Query("mbid") mbid: String
    ): Call<TopArtistsResponse>
}