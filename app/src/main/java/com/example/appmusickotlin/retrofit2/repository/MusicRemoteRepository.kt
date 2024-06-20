package com.example.appmusickotlin.retrofit2.repository

import com.example.appmusickotlin.model.Song
import com.example.appmusickotlin.retrofit2.InstanceRetrofit.RetrofitClient
import com.example.appmusickotlin.retrofit2.mapper.toSong
import com.example.appmusickotlin.retrofit2.model.MusicRemote
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MusicRemoteRepository {
    private val apiService = RetrofitClient.apiService

    fun getMusic(callback: (MutableList<Song>?, Throwable?) -> Unit) {
        apiService.getSongs().enqueue(object : Callback<MutableList<MusicRemote>> {
            override fun onResponse(call: Call<MutableList<MusicRemote>>, response: Response<MutableList<MusicRemote>>) {
                if (response.isSuccessful) {
                    val musicList = response.body()?.map { it.toSong() }?.toMutableList()
                    callback(musicList, null)
                } else {
                    callback(null, Throwable("Lỗi HTTP ${response.code()}: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<MutableList<MusicRemote>>, t: Throwable) {
                callback(null, t)
            }
        })
    }


}
