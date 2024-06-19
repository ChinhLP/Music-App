package com.example.appmusickotlin.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Song(
    var id : Long = System.currentTimeMillis(),
    var name : String? = null,
    var duration : Long? = null,
    var kind : String? = null,
    var albumId : Long? = null,
    var artist : String? = null,
    var path : String? = null,
    var playlistId : Long? = 0
) : Serializable