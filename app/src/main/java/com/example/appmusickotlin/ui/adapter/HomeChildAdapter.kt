package com.example.appmusickotlin.ui.adapter

import android.util.Log
import android.util.Printer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusickotlin.data.remoteRetrofit.model.Album
import com.example.appmusickotlin.data.remoteRetrofit.model.Artist
import com.example.appmusickotlin.data.remoteRetrofit.model.Track
import com.example.appmusickotlin.databinding.PlaylistItemLayoutBinding
import com.example.appmusickotlin.databinding.TopAlbumItemLayoutBinding
import com.example.appmusickotlin.databinding.TopArtistItemLayoutBinding
import com.example.appmusickotlin.databinding.TopTracksItemLayoutBinding
import com.example.appmusickotlin.model.ParentItem

class HomeChildAdapter(
    private var albumList: MutableList<Album>? = null,
    private var artistList: MutableList<Artist>? = null,
    private var trackList: MutableList<Track>? = null,
    private var type: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class AlbumViewHolder(val binding: TopAlbumItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class ArtistViewHolder(val binding: TopArtistItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class TrackViewHolder(val binding: TopTracksItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (type) {
            1 -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = TopAlbumItemLayoutBinding.inflate(inflater, parent, false)
                AlbumViewHolder(binding)
            }
            3 -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = TopTracksItemLayoutBinding.inflate(inflater, parent, false)
                TrackViewHolder(binding)
            }
            2 -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = TopArtistItemLayoutBinding.inflate(inflater, parent, false)
                ArtistViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Unknown view type: $type")
        }
    }

    override fun getItemCount(): Int {
        return when (type) {
            1 -> albumList?.size ?: 0
            2 -> artistList?.size ?: 0
            3 -> trackList?.size ?: 0
            else -> 0
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (type) {
            1 -> {
                val albumItem = albumList!![position]
                val albumViewHolder = holder as AlbumViewHolder
                albumViewHolder.binding.txtTitle.text = albumItem.name
                albumViewHolder.binding.txtNumber.text = albumItem.artist?.name ?: ""
                // You may need to bind other properties as well
            }
            3 -> {
                val trackItem = trackList!![position]
                val trackViewHolder = holder as TrackViewHolder
                trackViewHolder.binding.songTitleTextView.text = trackItem.name
                trackViewHolder.binding.txtNumberPlay.text = trackItem.playcount.toString()
                trackViewHolder.binding.txtListeners.text = trackItem.listeners.toString()
                // You may need to bind other properties as well
            }
            2 -> {
                val artistItem = artistList!![position]
                val artistViewHolder = holder as ArtistViewHolder
                artistViewHolder.binding.songTitleTextView.text = artistItem.name
            }
        }
    }
}