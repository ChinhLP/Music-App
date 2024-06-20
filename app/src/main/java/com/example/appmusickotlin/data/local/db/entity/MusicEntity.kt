package com.example.appmusickotlin.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "musics")
data class MusicEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = System.currentTimeMillis(),
    val title: String,
    val artist: String,
    val albumId: Long,
    val duration: Long,
    val data : String,
    val playlistId: Long // Foreign key reference to PlaylistEntity
)