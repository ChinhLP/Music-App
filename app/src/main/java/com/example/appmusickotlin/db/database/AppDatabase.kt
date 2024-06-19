package com.example.appmusickotlin.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appmusickotlin.db.dao.MusicDao
import com.example.appmusickotlin.db.dao.PlaylistDao
import com.example.appmusickotlin.db.dao.UserDao
import com.example.appmusickotlin.db.entity.MusicEntity
import com.example.appmusickotlin.db.entity.PlaylistEntity
import com.example.appmusickotlin.db.entity.UserEntity

@Database(entities = [UserEntity::class, PlaylistEntity::class, MusicEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun playlistDao(): PlaylistDao
    abstract fun musicDao(): MusicDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}