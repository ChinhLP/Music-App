package com.example.appmusickotlin.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appmusickotlin.db.entity.MusicEntity

@Dao
interface MusicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMusic(music: MusicEntity)

    @Query("SELECT * FROM musics WHERE playlistId = :playlistId")
    fun getMusicsByPlaylistId(playlistId: Long): LiveData<MutableList<MusicEntity>>

    @Query("SELECT * FROM musics")
     fun getAllMusics(): LiveData<MutableList<MusicEntity>>

    @Query("DELETE FROM musics WHERE id = :id")
      fun deleteMusic(id: Long)

    @Query("DELETE FROM musics WHERE playlistId = :playlistId")
    suspend fun deleteAllMusic(playlistId: Long)

    // Các phương thức khác như update, delete, query tùy vào nhu cầu

}