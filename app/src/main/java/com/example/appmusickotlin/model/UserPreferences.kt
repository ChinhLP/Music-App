package com.example.appmusickotlin.model
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.appmusickotlin.ui.authetication.AuthActivity
import com.google.gson.Gson
import com.orhanobut.hawk.Hawk
import java.io.Serializable


data class MyUser(
    var userId: Long? = 0,
    var username: String? = "",
    var email: String? = "",
    var password: String? = "",
    var rePassword: String? = "",
    var imageAvatar: String? = "",
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

fun logout(context: Context) {
    Hawk.delete("user")
    User.userId = null
    User.username = null
    User.email = null
    User.password = null
    User.imageAvatar = null

    val intent = Intent(context, AuthActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
    if (context is Activity) {
        context.finish()
    }
}

fun setMyUser() : MyUser {
    val user = MyUser()
    user.userId = User.userId
    user.username = User.username
    user.password = User.password
    user.email = User.email
    user.imageAvatar = User.imageAvatar
    //user.albumsLst = User.albumsLst

    return user
}
