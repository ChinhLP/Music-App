package com.example.appmusickotlin.util.callBack

import android.view.View
import android.widget.TextView
import com.example.appmusickotlin.model.DataListPlayList
import com.example.appmusickotlin.model.Song

interface OnEditButtonClickListener {
    fun onEditButtonClick(song: Song)
    fun onDeleteButtonClick(song: Song, position: Int)
}


interface PlaylistAddedListener {
    fun onPlaylistAdded(album: DataListPlayList)
}


interface OnItemClickListener {
    fun onItemClick(position: Int,idPlayList : Long)

}

interface OnMusicClickListener {
    fun onItemClick(song: Song , playList: MutableList<Song>)
}

interface OnEditPopupAlbumButtonClickListener {
    fun onEditPopupAlbumButtonClick(view: View, albumId: Long)
}

interface OnSeeALLListener{
    fun onSeeAll(type: Int)
}

interface NumberMusicInPlaylistListener {
    fun numberMusicInPlaylistListener(textView: TextView,albumId : Long)
}