package com.example.appmusickotlin.data.remoteRetrofit.repository

import android.util.Log
import com.example.appmusickotlin.data.remoteRetrofit.InstanceRetrofit.HomeRetrofitClient
import com.example.appmusickotlin.data.remoteRetrofit.InstanceRetrofit.RetrofitClient
import com.example.appmusickotlin.data.remoteRetrofit.model.Artist
import com.example.appmusickotlin.data.remoteRetrofit.model.TopArtistsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ArtistRepository {
    private val artistApiService = HomeRetrofitClient.artistApiService

    fun getTopArtists(
        apiKey: String,
        format: String,
        method: String,
        callback: (MutableList<Artist>?, Throwable?) -> Unit
    ) {
        artistApiService.getTopArtists(apiKey, format, method).enqueue(object : Callback<TopArtistsResponse> {
            override fun onResponse(call: Call<TopArtistsResponse>, response: Response<TopArtistsResponse>) {
                if (response.isSuccessful) {
                    val artistList = response.body()?.artists?.artistList?.toMutableList()
                    callback(artistList, null)
                } else {
                    Log.e("ArtistRepository", "HTTP error ${response.code()}: ${response.message()}")
                    callback(null, Throwable("HTTP error ${response.code()}: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<TopArtistsResponse>, t: Throwable) {
                Log.e("ArtistRepository", "Failed to fetch top artists", t)
                callback(null, t)
            }
        })
    }
}