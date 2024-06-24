package com.example.appmusickotlin.data.remoteRetrofit.repository

import com.example.appmusickotlin.data.remoteRetrofit.InstanceRetrofit.HomeRetrofitClient
import com.example.appmusickotlin.data.remoteRetrofit.model.Album
import com.example.appmusickotlin.data.remoteRetrofit.model.TopAlbumsResponse
import com.example.appmusickotlin.data.remoteRetrofit.model.TopTrackResponse
import com.example.appmusickotlin.data.remoteRetrofit.model.Track
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrackRepository {
    private val trackApiService = HomeRetrofitClient.trackApiService

    fun getTopTrack(
        apiKey: String,
        format: String,
        method: String,
        mbid: String,
        callback: (MutableList<Track>?, Throwable?) -> Unit
    ) {
        trackApiService.getTopTrack(apiKey, format, method, mbid)
            .enqueue(object : Callback<TopTrackResponse> {
                override fun onResponse(
                    call: Call<TopTrackResponse>,
                    response: Response<TopTrackResponse>
                ) {
                    if (response.isSuccessful) {
                        val trackList = response.body()?.track?.listTrack?.toMutableList()
                        callback(trackList, null)
                    } else {
                        callback(
                            null,
                            Throwable("HTTP error ${response.code()}: ${response.message()}")
                        )
                    }
                }

                override fun onFailure(call: Call<TopTrackResponse>, t: Throwable) {
                    callback(null, t)
                }
            })
    }
}