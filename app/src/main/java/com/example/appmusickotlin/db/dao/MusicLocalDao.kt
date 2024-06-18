package com.example.appmusickotlin.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appmusickotlin.db.entity.MusicLocal

@Dao
interface MusicLocalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(musics: MutableList<MusicLocal>)

    @Query("SELECT * FROM music_table")
     fun getAllMusic(): LiveData<MutableList<MusicLocal>>

    @Query("DELETE FROM music_table")
     fun deleteAll()


}