package com.example.appmusickotlin.data.remoteRetrofit.api

import com.example.appmusickotlin.data.remoteRetrofit.model.TopAlbumsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumApiService {
    @GET("2.0/")
    fun getTopAlbums(
        @Query("api_key") apiKey: String,
        @Query("format") format: String,
        @Query("method") method: String,
        @Query("mbid") mbid:String
    ) : Call<TopAlbumsResponse>
}