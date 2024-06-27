package com.example.appmusickotlin.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    var numberMusic : Long? = 0,
    val userId: Long // Foreign key reference to UserEntity
)