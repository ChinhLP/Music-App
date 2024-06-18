package com.example.appmusickotlin.db.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.appmusickotlin.db.dao.UserDao
import com.example.appmusickotlin.db.database.AppDatabase
import com.example.appmusickotlin.db.entity.UserEntity
import com.example.appmusickotlin.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {
    val allUsers: LiveData<List<UserEntity>> = userDao.getAllUsers()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)


    suspend fun insert(user: UserEntity) {
        coroutineScope.launch {
            userDao.insertUser(user)
        }
    }

    suspend fun getUser(username : String , password : String): UserEntity? {

        return withContext(Dispatchers.IO) {
            userDao.getUser(username, password)
        }
    }

}