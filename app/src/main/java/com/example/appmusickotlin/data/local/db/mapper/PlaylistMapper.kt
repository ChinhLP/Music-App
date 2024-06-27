package com.example.appmusickotlin.data.local.db.mapper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.appmusickotlin.data.local.db.entity.PlaylistEntity
import com.example.appmusickotlin.model.DataListPlayList

// Hàm mở rộng để chuyển đổi MutableList<PlaylistEntity> thành MutableList<DataListPlayList>
fun LiveData<MutableList<PlaylistEntity>>.toDataListPlayListLiveData(): LiveData<MutableList<DataListPlayList>> {
    val result = MediatorLiveData<MutableList<DataListPlayList>>()
    result.addSource(this) { playlistEntities ->
        val dataList = mutableListOf<DataListPlayList>()
        playlistEntities?.let {
            for (playlistEntity in it) {
                val dataListPlayList = DataListPlayList(
                    id = playlistEntity.id,
                    title = playlistEntity.name,
                    numberMusic = playlistEntity.numberMusic,
                    userId = playlistEntity.userId
                )
                dataList.add(dataListPlayList)
            }
            result.value = dataList
        }
    }
    return result
}
fun DataListPlayList.toPlaylistEntity(): PlaylistEntity {
    return PlaylistEntity(
        id = this.id,
        name = this.title,
        numberMusic = this.numberMusic,
        userId = this.userId
    )
}

fun PlaylistEntity.toDataListPlayList(): DataListPlayList {
    return DataListPlayList(
        id = this.id,
        title = this.name,
        numberMusic = this.numberMusic,
        userId = this.userId
        // Các trường khác nếu có
    )
}