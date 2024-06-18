package com.example.appmusickotlin.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "music_table")
data class MusicLocal(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String?,
    val duration: Long,
    val albumId: Long,
    val artist: String?,
    val data: String?
)