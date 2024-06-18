package com.example.appmusickotlin.ui.authetication.viewmodel

import android.app.Application
import androidx.compose.ui.window.application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.appmusickotlin.db.dao.UserDao
import com.example.appmusickotlin.db.database.AppDatabase
import com.example.appmusickotlin.db.entity.UserEntity
import com.example.appmusickotlin.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel(){
    private var _currentFragment = MutableLiveData<String>()
    var currentFragment: LiveData<String> = _currentFragment





    fun navigateToSignIn() {
        _currentFragment.value = "SignIn"
    }

    fun navigateToSignUp() {
        _currentFragment.value = "SignUp"

    }

}