package com.example.appmusickotlin.data.remoteRetrofit.repository

import android.util.Log
import com.example.appmusickotlin.data.remoteRetrofit.InstanceRetrofit.ArtistRetrofitClient
import com.example.appmusickotlin.data.remoteRetrofit.InstanceRetrofit.RetrofitClient
import com.example.appmusickotlin.data.remoteRetrofit.model.Artist
import com.example.appmusickotlin.data.remoteRetrofit.model.TopArtistsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArtistRepository1 {
    private val artistApiService = ArtistRetrofitClient.artistApiService

    fun getTopArtists(
//        apiKey: String,
//        format: String,
//        method: String,
//        mbid: String,
        callback: (MutableList<Artist>?, Throwable?) -> Unit
    ) {
        // Using enqueue to perform an asynchronous network request
        artistApiService.getTopArtists().enqueue(object :
            Callback<TopArtistsResponse> {
            override fun onResponse(call: Call<TopArtistsResponse>, response: Response<TopArtistsResponse>) {
                if (response.isSuccessful) {
                    // Successful response, parse the list of artists
                    val artistList = response.body()?.artists?.artistList?.toMutableList()
                    callback(artistList, null)
                } else {
                    // Handle unsuccessful response
                    Log.e("ArtistRepository", "HTTP error ${response.code()}: ${response.message()}")
                    callback(null, Throwable("HTTP error ${response.code()}: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<TopArtistsResponse>, t: Throwable) {
                // Network call failure, handle the exception
                Log.e("ArtistRepository", "Failed to fetch top artists", t)
                callback(null, t)
            }
        })
    }
}


class ArtistRepository {
    private val artistApiService = ArtistRetrofitClient.artistApiService

    fun getTopArtists(
        //        apiKey: String,
//        format: String,
//        method: String,
//        mbid: String,
        callback: (MutableList<Artist>?, Throwable?) -> Unit
    ) {
        artistApiService.getTopArtists().enqueue(object :
            Callback<TopArtistsResponse> {
            override fun onResponse(call: Call<TopArtistsResponse>, response: Response<TopArtistsResponse>) {
                if (response.isSuccessful) {
                    val artistList = response.body()?.artists?.artistList?.toMutableList()
                    callback(artistList, null)
                } else {
                    // Handle unsuccessful response
                    Log.e("ArtistRepository", "HTTP error ${response.code()}: ${response.message()}")
                    callback(null, Throwable("HTTP error ${response.code()}: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<TopArtistsResponse>, t: Throwable) {
                // Network call failure, handle the exception
                Log.e("ArtistRepository", "Failed to fetch top artists", t)
                callback(null, t)
            }
        })
    }
}
