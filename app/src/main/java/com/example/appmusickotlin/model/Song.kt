package com.example.appmusickotlin.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Song(
    val id : String? = null,
    val name : String? = null,
    val duration : Long? = null,
    val albumId : Long? = null,
) : Serializable