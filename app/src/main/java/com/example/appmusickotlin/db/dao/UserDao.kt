package com.example.appmusickotlin.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appmusickotlin.db.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE username = :userName AND password = :password")
    suspend fun getUser(userName: String, password: String): UserEntity?

    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<UserEntity>>
    // Các phương thức khác như update, delete, query tùy vào nhu cầu
}