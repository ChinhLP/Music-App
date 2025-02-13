package com.example.appmusickotlin.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = System.currentTimeMillis(),
    val username: String,
    val email: String,
    val password: String,
    var imageAvatar: String? = ""
)