package com.example.appmusickotlin.model

import java.io.Serializable


data class DataListPlayList(
    var id : Long = System.currentTimeMillis(),
    var title : String,
    var userId : Long = 0
    //var listMusic : MutableList<Song>? = mutableListOf()
) : Serializable