package com.example.appmusickotlin.ui.authetication.viewmodel

import android.app.Application
import androidx.compose.ui.window.application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.appmusickotlin.data.local.db.dao.UserDao
import com.example.appmusickotlin.data.local.db.database.AppDatabase
import com.example.appmusickotlin.data.local.db.entity.UserEntity
import com.example.appmusickotlin.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel(){
    private var _currentFragment = MutableLiveData<String>()
    var currentFragment: LiveData<String> = _currentFragment
    private  var _user = MutableLiveData<UserEntity>()
    var user: LiveData<UserEntity> = _user




    fun signUp(user : UserEntity) {
        _user.value = user
    }


    fun navigateToSignIn() {
        _currentFragment.value = "SignIn"
    }

    fun navigateToSignUp() {
        _currentFragment.value = "SignUp"

    }

}