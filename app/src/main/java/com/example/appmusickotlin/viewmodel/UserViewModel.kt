package com.example.appmusickotlin.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appmusickotlin.db.database.AppDatabase
import com.example.appmusickotlin.db.entity.UserEntity
import com.example.appmusickotlin.db.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val _userRepository: UserRepository
    private var _user = MutableLiveData<UserEntity>()

    var user : LiveData<UserEntity> = _user
    val allUsers: LiveData<List<UserEntity>>

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        _userRepository = UserRepository(userDao)
        allUsers = _userRepository.allUsers
    }

    fun insert(user: UserEntity) = viewModelScope.launch {
        _userRepository.insert(user)
    }

    fun getUser(username : String, password : String ) = viewModelScope.launch {
        _user.value =  _userRepository.getUser(username, password)
        Log.d("hhh","user: ${user.value}")

    }
}