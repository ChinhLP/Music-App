package com.example.appmusickotlin.model


object User  {
    var userId: Long? = System.currentTimeMillis()
    var username: String? = ""
    var email: String? = ""
    var password: String? = ""
    //var albumsLst: MutableList<DataListPlayList> = mutableListOf()
}