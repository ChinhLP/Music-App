package com.example.appmusickotlin.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appmusickotlin.db.entity.PlaylistEntity


@Dao
interface PlaylistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: PlaylistEntity)

    @Query("SELECT * FROM playlists WHERE userId = :userId")
    suspend fun getPlaylistsByUserId(userId: Long): MutableList<PlaylistEntity>

    @Query("SELECT * FROM playlists")
    fun getAllUsers(): LiveData<MutableList<PlaylistEntity>>

    @Query("DELETE FROM playlists WHERE id = :id")
    suspend fun deletePlaylist(id: Long)


    // Các phương thức khác như update, delete, query tùy vào nhu cầu
}