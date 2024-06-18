package com.example.appmusickotlin.db.mapper

import com.example.appmusickotlin.db.entity.PlaylistEntity
import com.example.appmusickotlin.model.DataListPlayList

// Hàm mở rộng để chuyển đổi MutableList<PlaylistEntity> thành MutableList<DataListPlayList>
fun MutableList<PlaylistEntity>.toDataListPlayList(): MutableList<DataListPlayList> {

    val dataList = mutableListOf<DataListPlayList>()

    for (playlistEntity in this) {
        val dataListPlayList = DataListPlayList(
            id = playlistEntity.id,
            title = playlistEntity.name,
            userId = playlistEntity.userId
        )
        dataList.add(dataListPlayList)
    }
    return dataList
}

fun DataListPlayList.toPlaylistEntity(): PlaylistEntity {
    return PlaylistEntity(
        id = this.id,
        name = this.title,
        userId = this.userId
    )
}
