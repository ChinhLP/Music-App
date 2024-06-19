package com.example.appmusickotlin.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val userId: Long // Foreign key reference to UserEntity
)