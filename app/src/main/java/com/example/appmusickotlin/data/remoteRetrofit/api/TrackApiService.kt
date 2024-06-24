package com.example.appmusickotlin.data.remoteRetrofit.api

import com.example.appmusickotlin.data.remoteRetrofit.model.TopTrackResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TrackApiService {
    @GET("2.0/")
    fun getTopTrack(
        @Query("api_key") apiKey: String,
        @Query("format") format: String,
        @Query("method") method: String,
        @Query("mbid") mbid:String
    ) : Call<TopTrackResponse>
}