package com.example.appmusickotlin.data.remoteRetrofit.repository

import android.util.Log
import com.example.appmusickotlin.data.remoteRetrofit.InstanceRetrofit.HomeRetrofitClient
import com.example.appmusickotlin.data.remoteRetrofit.model.Album
import com.example.appmusickotlin.data.remoteRetrofit.model.Albums
import com.example.appmusickotlin.data.remoteRetrofit.model.Artist
import com.example.appmusickotlin.data.remoteRetrofit.model.TopAlbumsResponse
import com.example.appmusickotlin.data.remoteRetrofit.model.TopArtistsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class AlbumRepository {
    private val albumApiService = HomeRetrofitClient.albumApiService

    fun getTopAlbums(
        apiKey: String,
        format: String,
        method: String,
        mbid: String,
        callback: (MutableList<Album>?, Throwable?) -> Unit
    ) {
        albumApiService.getTopAlbums(apiKey, format, method, mbid)
            .enqueue(object : Callback<TopAlbumsResponse> {
                override fun onResponse(
                    call: Call<TopAlbumsResponse>,
                    response: Response<TopAlbumsResponse>
                ) {
                    if (response.isSuccessful) {
                        val albumList = response.body()?.albums?.albumList?.toMutableList()
                        callback(albumList, null)
                    } else {
                        callback(
                            null,
                            Throwable("HTTP error ${response.code()}: ${response.message()}")
                        )
                    }
                }

                override fun onFailure(call: Call<TopAlbumsResponse>, t: Throwable) {
                    callback(null, t)
                }
            })
    }
}