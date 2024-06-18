package com.example.appmusickotlin.model
import android.util.Log
import com.google.gson.Gson
import com.orhanobut.hawk.Hawk
import java.io.Serializable


data class MyUser(
    var userId: Long? = 0,
    var username: String? = "",
    var email: String? = "",
    var password: String? = "",
    var rePassword: String? = "",
    var albumsLst: MutableList<DataListPlayList> = mutableListOf(),
) : Serializable


fun saveUser(user : MyUser){
    val gson = Gson()
    val userJson = gson.toJson(user)
    Hawk.put("user",userJson)
    Log.e("ec", "saveUser: $userJson")
}

fun getUser() : MyUser? {
    val gson = Gson()
    val userJson = Hawk.get<String>("user")
    if(!userJson.isNullOrEmpty()){
        val user = gson.fromJson(userJson, MyUser::class.java)
        return user
    } else {
        return null
    }
}

fun isLoggedIn(): Boolean {
    return Hawk.contains("user")
}

fun setMyUser() : MyUser {
    val user = MyUser()
    user.userId = User.userId
    user.username = User.username
    user.password = User.password
    user.email = User.email
    //user.albumsLst = User.albumsLst

    return user
}