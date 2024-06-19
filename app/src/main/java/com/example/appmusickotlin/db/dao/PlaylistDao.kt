package com.example.appmusickotlin.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.appmusickotlin.db.entity.PlaylistEntity


@Dao
interface PlaylistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: PlaylistEntity)

    @Query("SELECT * FROM playlists WHERE userId = :userId")
    fun getPlaylistsByUserId(userId: Long): LiveData<MutableList<PlaylistEntity>>

    @Query("SELECT * FROM playlists")
    fun getAllUsers(): LiveData<MutableList<PlaylistEntity>>

    @Query("DELETE FROM playlists WHERE id = :id")
    suspend fun deletePlaylist(id: Long)

    @Query("SELECT * FROM playlists WHERE id = :id")
    suspend fun getPlaylistById(id: Long): PlaylistEntity?

    @Update
    suspend fun updatePlaylistName(playlist: PlaylistEntity)


    // Các phương thức khác như update, delete, query tùy vào nhu cầu
}